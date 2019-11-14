package MiniBlog.Main.ServeImpl.Article;


import MiniBlog.Main.Dao.Article.ArticleDao;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServeImpl {
    @Autowired
    ArticleDao dao;

    public Map<String, String> addNewArticle(Map<String, String> param){
        Map<String, String> result = new HashMap<>();
        result.put("result", "false");
        Integer newArticleId = dao.insertNewArticle(param);
        if(1==newArticleId){
            param.put("articleId", String.valueOf(param.get("id")));
            if(1==dao.insertNewArticleContent(param)) {
                result.put("result", "true");
                result.put("newArticleId", param.get("articleId"));
            }
        }
        return result;
    }

    public int addNewImg(String articleId, List<String> list){
        return dao.insertNewImgList(articleId, list);
    }

}
