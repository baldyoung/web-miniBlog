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

    /**
     *  #{userId} LIMIT #{firstIndex}, #{maxAmount};
     *  获取指定用户有效的已发文章/帖子，所谓有效即没有被封禁的文章/帖子
     *  map中的参数需求{userId:要查询的用户Id, firstIndex:分页参数-第一条数据的小标, maxAmount:分页参数-最多几条数据}
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAvailableArticleListByUserId(Map param);

    public List<String> getPictureListByArticleId(@Param("articleId") String articleId);

    /**
     * 获取指定用户有效帖子的总数
     * @param userId
     * @return
     */
    public Integer getAmountOfAvailableArticleListByUserId(@Param("userId")Integer userId);

    public Integer getTheLikeFlagOfTheArticle(@Param("articleId")Integer articleId, @Param("userId")Integer userId);

    public Integer insertNewLikeFlagOfTheArticle(@Param("articleId")Integer articleId, @Param("userId")Integer userId);

    public Integer deleteOldLikeFlagOfTheArticle(@Param("recodeId")Integer recodeId);
}
