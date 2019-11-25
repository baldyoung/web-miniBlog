package MiniBlog.Main.Controller.Front.Management;


import MiniBlog.Main.Common.ConfigurationModule;
import MiniBlog.Main.Common.Enum.ResultErrorInf;
import MiniBlog.Main.Common.FileDataOption;
import MiniBlog.Main.Common.Result;
import MiniBlog.Main.Common.Utils.CommonUtil;
import MiniBlog.Main.Common.WorkingPoolModule;
import MiniBlog.Main.ServeImpl.Article.ArticleServeImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.System.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/WebContext/MiniBlog_Front/X_Management")
public class ManagementController {

    static private volatile String ImgPath = null;
    @Autowired
    ArticleServeImpl serve;
    @Autowired
    ConfigurationModule configModule;


    @RequestMapping(value = "/management", method = {GET})
    public String defaultMethod() {
        return "MiniBlog_Front/X_Management/managementX";
    }

    public Map<String, Object> getArticleList(@RequestParam(value = "first", defaultValue = "1")Integer first, @RequestParam(value = "num", defaultValue = "5")Integer num) {

        return null;
    }

    /**
     * 用户发帖接口
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addNewArticle", method = {POST})
    @ResponseBody
    public Result addNewArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Result result = new Result();
        //out.println("addNewArticle get request");
        //out.println("one arg : " + request.getParameter("NewImgNum"));
        //out.println("one arg : " + request.getParameter("img0"));
        // 1.创建DiskFileItemFactory对象，配置缓存用
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        // 2. 创建 ServletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        // 3. 设置文件名称编码
        servletFileUpload.setHeaderEncoding("utf-8");
        //新增文章的基本信息
        Map<String, Object> newArticleParam = new HashMap<>();
        //新增文章里附带图片的InputStream流的集合
        List<InputStream> inputStreamList = new LinkedList<>();
        List<FileItem> items;
        try {
            items = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : items) {
                if (fileItem.isFormField()) { // >> 普通数据
                    newArticleParam.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                } else { // >> 文件
                    String name = fileItem.getName(); //获取文件名称
                    InputStream is = fileItem.getInputStream();  // 获取文件的实际内容
                    inputStreamList.add(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件数据解析失败");
        }
        HttpSession session = request.getSession();
        String userId = null;
        // if (null != session.getAttribute("account")) userId = session.getAttribute("account").toString();
        // else out.println("userId is null");  //这里应该变更一下，如果无法获取到登录者的编号，应该给他返回为失败
        userId = session.getAttribute("userId").toString(); //  --------------------------------------------------- --------------------------------------------------------- 这里的用户id暂时为了方便测试，所以写死了等于123
        newArticleParam.put("userId", userId);
        //out.println("request param : " + newArticleParam);  //  test line !!!!!
        //String test = request.getContextPath();
        //test = request.getServletContext().getRealPath("");
        //初始化图片存储路径
        if (null == ImgPath) {
            synchronized (this) {
                ImgPath = request.getServletContext().getRealPath("") + "/" + configModule.getConfigParameter("ImgDirectory") + "/";
                out.println("图片的存储路径为：" + ImgPath);
            }
        }
        // 将图片数据打包成指定集合
        List<FileDataOption> newImgs = FileDataOption.getListByInputStreams(inputStreamList, ImgPath, ".jpg");
        // 将图片集数据放入参数集中
        newArticleParam.put("newImgList", newImgs);
        // 将新文章的基本数据保存到数据库
        Map<String, String> actionResult = serve.addNewArticle(newArticleParam);
        if (CommonUtil.isAnyNullObject(actionResult, actionResult.get("result")) || !"true".equals(actionResult.get("result"))) {
            return Result.fail("新增失败");
        }
        return Result.success(null);
    }

    @RequestMapping("/getArticleList")
    @ResponseBody
    public Result getArticleList( @RequestParam(value="firstIndex")Integer firstIndex, @RequestParam(value="maxAmount")Integer maxAmount, HttpSession session) {
        Integer userId = null;
        if (null == session || null == session.getAttribute("userId")) {
            return Result.fail(ResultErrorInf.USER_UNLOGIN);
        }
        userId = Integer.parseInt(session.getAttribute("userId").toString());
        List<Map<String, Object>> data;
        // 参数为空检查
        if (CommonUtil.isAnyNullObject(userId, firstIndex, maxAmount)) {
            return Result.fail(ResultErrorInf.PARAM_IS_EMPTY);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("firstIndex", firstIndex);
        param.put("maxAmount", maxAmount);
        data = serve.getAvailableArticleListByUserId(param);
        if (null == data) {
            return Result.fail(ResultErrorInf.DATA_REQUEST_FAIL);
        }
        return Result.success(data);
    }

}
