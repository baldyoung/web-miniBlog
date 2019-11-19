package MiniBlog.Main.ServeImpl.Article;


import MiniBlog.Main.Common.FileDataOption;
import MiniBlog.Main.Common.WorkingPoolModule;
import MiniBlog.Main.Dao.Article.ArticleDao;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServeImpl {
    @Autowired
    ArticleDao dao;
    @Autowired
    WorkingPoolModule ImgWorkingPool;

    /**
     * 用户发布article
     * @param param
     * @return
     */
    public Map<String, String> addNewArticle(Map<String, Object> param){
        Map<String, String> result = new HashMap<>();
        result.put("result", "false");
        // 新增一个article对象到数据库
        Integer newArticleId = dao.insertNewArticle(param);
        if(1 == newArticleId) {
            // 重新设置新增的article对象的编号
            param.put("articleId", String.valueOf(param.get("id")));
            // 将article对象的详细文本内容存放到数据库
            if(1 == dao.insertNewArticleContent(param)) {
                result.put("result", "true");
                result.put("newArticleId", param.get("articleId").toString());
            }
            // 将article内的图片集进行存储
            List<FileDataOption> newImgs = (List<FileDataOption>)param.get("newImgList");
            List<String> newImgNameList = new LinkedList<>();
            for (FileDataOption temp : newImgs) {
                String fileName = temp.getPathName();
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                newImgNameList.add(fileName);
                //System.out.println(fileName+"sourceData:"+temp.getPathName());
            }
            if (0 != newImgNameList.size()) {
                // 将图片名称存储到数据库
                dao.insertNewImgList(newArticleId.toString(), newImgNameList);
                // 将图片数据存储到指定路径
                ImgWorkingPool.submit(newImgs);
            }
            result.put("result", "true");
        }
        return result;
    }

    public int addNewImg(String articleId, List<String> list){
        return dao.insertNewImgList(articleId, list);
    }

}
