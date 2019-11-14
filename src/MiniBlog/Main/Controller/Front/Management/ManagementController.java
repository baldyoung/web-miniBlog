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
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Management")
public class ManagementController {

    static private volatile String ImgPath=null;
    @Autowired
    ArticleServeImpl serve;
    @Autowired
    ConfigurationModule configModule;
    @Autowired
    WorkingPoolModule ImgWorkingPool;

    @RequestMapping(value="/management", method={GET})
    public String defaultMethod(){
        return "MiniBlog_Front/X_Management/managementX";
    }

    @RequestMapping(value="/addNewArticle", method={POST})
    @ResponseBody
    public Map<String, String> addNewArticle(HttpServletRequest request, HttpServletResponse response)throws Exception{
        Map<String, String> result = new HashMap<>();
        result.put("result", "true");
        out.println("addNewArticle get request");
        out.println("one arg : "+request.getParameter("NewImgNum"));
        out.println("one arg : "+request.getParameter("img0"));
        // 1.创建DiskFileItemFactory对象，配置缓存用
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        // 2. 创建 ServletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        // 3. 设置文件名称编码
        servletFileUpload.setHeaderEncoding("utf-8");
        //新增文章的基本信息
        Map<String, String> newArticleParam = new HashMap<>();
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
        String userId=null;
        if(null != session.getAttribute("account")) userId = session.getAttribute("account").toString();
        else out.println("userId is null");  //这里应该变更一下，如果无法获取到登录者的编号，应该给他返回为失败
        userId = "123";
        newArticleParam.put("userId", userId);
        out.println("request param : "+newArticleParam);  //  test line !!!!!
        //初始化图片存储路径
        if(null == ImgPath){
            synchronized(this){
                ImgPath = request.getServletContext().getResource("").getPath().toString() + configModule.getConfigParameter("ImgDirectory")+"/";
                out.println("图片的存储路径为："+ImgPath);
            }
        }
        //将新文章的基本数据保存到数据库
        result = serve.addNewArticle(newArticleParam);
        if("true".equals(result.get("result"))){//数据成功添加到数据库
            //将上传的图片保存到指定目录下的同时保存图片名称到数据库
            String newArticleId = result.get("articleId");
            List<FileDataOption> newImgs = FileDataOption.getListByInputStreams(inputStreamList, ImgPath, ".jpg");
            List<String> newImgNameList = new LinkedList<>();
            for(FileDataOption temp : newImgs)
                newImgNameList.add(temp.getPathName());
            //向数据库添加新增图片的名称
            //serve.addNewImg(newArticleId, newImgNameList);
            //将图片数据从流中取出，并保存到指定目录
            int t=0;
            for(FileDataOption fdo : newImgs){
                if(fdo.saveData()) t++;
            }
            //t= ImgWorkingPool.submit(newImgs);
            out.println("尝试新增"+newImgs.size()+"张图片");
            out.println("成功新增"+t+"张图片");
        }
        return  result;
    }

}
