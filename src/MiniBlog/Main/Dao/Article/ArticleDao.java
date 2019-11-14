package MiniBlog.Main.Dao.Article;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleDao {

    public int insertNewArticle(Map param);

    public int insertNewArticleContent(Map param);

    public int insertNewImgList(@Param("articleId")String articleId, @Param("list") List<String> list);

}
