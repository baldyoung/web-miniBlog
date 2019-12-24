package MiniBlog.Main.Controller.Front.UserManager;

import MiniBlog.Main.Common.ConfigurationModule;
import MiniBlog.Main.Common.Utils.CommonUtil;
import MiniBlog.Main.Common.Utils.FileDataOption;
import MiniBlog.Main.Common.WorkingPoolModule;
import MiniBlog.Main.Common.response.Result;
import MiniBlog.Main.ServeImpl.User.UserServeImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
@RequestMapping(value = {"/WebContext/MiniBlog_Front/X_UserManager"})
public class UserManagerController {

    static private volatile String ImgPath = null;

    @Autowired
    WorkingPoolModule ImgWorkingPool;

    @Autowired
    UserServeImpl userServe;

    @Autowired
    ConfigurationModule configModule;

    @RequestMapping(method = {GET})
    public String defaultMethod() {
        out.println("UserManagerController get request");
        return "redirect:/WebContext/MiniBlog_Front/X_UserManager/userManager";
    }


    @RequestMapping(value = {"/userManager"}, method = GET)
    public String indexMethod() {
        return "MiniBlog_Front/X_UserManager/userManager";
    }

    @RequestMapping(value = {"/userInfo"}, method = {GET, POST})
    @ResponseBody
    public Result getUserInfo(HttpSession session) {
        Integer userId = CommonUtil.getUserIdWithOutException(session);
        if (Objects.isNull(userId)) {
            return Result.fail();
        }
        Map<String, Object> data = userServe.getUserInfo(userId);
        if (Objects.isNull(data)) {
            return Result.fail();
        }
        return Result.success(data);
    }

    @RequestMapping(value = {"/updateUserInfo"}, method = {POST})
    @ResponseBody
    public Result updateUserInfo(HttpServletRequest request) {
        Integer userId = CommonUtil.getUserIdWithOutException(request.getSession());
        if (Objects.isNull(userId)) {
            return Result.fail();
        }
        // 1.创建DiskFileItemFactory对象，配置缓存用
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        // 2. 创建 ServletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        // 3. 设置文件名称编码
        servletFileUpload.setHeaderEncoding("utf-8");
        Map<String, Object> newUserInfo = new HashMap<>();
        List<InputStream> inputStreamList = new LinkedList<>();
        List<FileItem> items;
        try {
            items = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : items) {
                if (fileItem.isFormField()) { // >> 普通数据
                    newUserInfo.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                } else { // >> 文件
                    String name = fileItem.getName(); //获取文件名称
                    InputStream is = fileItem.getInputStream();  // 获取文件的实际内容
                    inputStreamList.add(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("数据解析失败");
        }
        //初始化图片存储路径
        if (null == ImgPath) {
            synchronized (this) {
                ImgPath = request.getServletContext().getRealPath("") + "/" + configModule.getConfigParameter("ImgDirectory") + "/";
                out.println("图片的存储路径为：" + ImgPath);
            }
        }
        // 将图片数据打包成指定集合
        List<FileDataOption> newImgs = FileDataOption.getListByInputStreams(inputStreamList, ImgPath, ".jpg");
        // 将图片保存到存储路径下

        newUserInfo.put("userId", userId);
        if (!Objects.isNull(newUserInfo.get("userAccount"))) {
            Map existAcount = userServe.queryByAccount(newUserInfo.get("userAccount").toString());
            if (!Objects.isNull(existAcount) && !Objects.isNull(existAcount.get("account"))) {
                return Result.fail("登录账号已存在");
            }
        }
        ImgWorkingPool.submit(newImgs);
        if (newImgs.size() > 0) {
            String pathName = newImgs.get(0).getPathName();
            String pictureName = pathName.substring(pathName.lastIndexOf(File.separator)+1);
            newUserInfo.put("userPicture", pictureName);
        }
        out.println(newUserInfo);
        if (userServe.updateUserInfo(newUserInfo)) {
            return Result.success();
        }
        return Result.fail();
    }


}
