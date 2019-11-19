package MiniBlog.Main.Controller.Front.Management;


import MiniBlog.Main.Common.ConfigurationModule;
import MiniBlog.Main.Common.FileDataOption;
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

    @RequestMapping(value = "/addNewArticle", method = {POST})
    @ResponseBody
    public Map<String, String> addNewArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> result = new HashMap<>();
        result.put("result", "true");
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
        }
        HttpSession session = request.getSession();
        String userId = null;
        if (null != session.getAttribute("account")) userId = session.getAttribute("account").toString();
        else out.println("userId is null");  //这里应该变更一下，如果无法获取到登录者的编号，应该给他返回为失败
        userId = "123";
        newArticleParam.put("userId", userId);
        //out.println("request param : " + newArticleParam);  //  test line !!!!!
        //初始化图片存储路径
        if (null == ImgPath) {
            synchronized (this) {
                ImgPath = request.getServletContext().getResource("").getPath().toString() + configModule.getConfigParameter("ImgDirectory") + "/";
                out.println("图片的存储路径为：" + ImgPath);
            }
        }
        // 将图片数据打包成指定集合
        List<FileDataOption> newImgs = FileDataOption.getListByInputStreams(inputStreamList, ImgPath, ".jpg");
        // 将图片集数据放入参数集中
        newArticleParam.put("newImgList", newImgs);
        //将新文章的基本数据保存到数据库
        result = serve.addNewArticle(newArticleParam);
        return result;
    }

}
