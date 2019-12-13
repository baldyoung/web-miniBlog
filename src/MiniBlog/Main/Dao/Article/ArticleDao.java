package MiniBlog.Main.Dao.Article;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleDao {
    /**
     * 新增一个帖子记录
     * @param param
     * @return
     */
    public int insertNewArticle(Map param);

    /**
     * 新增指定帖子的文字详情
     * @param param
     * @return
     */
    public int insertNewArticleContent(Map param);

    /**
     * 新增指定帖子下的图片数据
     * @param articleId
     * @param list
     * @return
     */
    public int insertNewImgList(@Param("articleId")String articleId, @Param("list") List<String> list);

    /**
     *  #{userId} LIMIT #{firstIndex}, #{maxAmount};
     *  获取指定用户有效的已发文章/帖子，所谓有效即没有被封禁的文章/帖子
     *  map中的参数需求{userId:要查询的用户Id, firstIndex:分页参数-第一条数据的小标, maxAmount:分页参数-最多几条数据}
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAvailableArticleListByUserId(Map param);

    /**
     * 获取指定帖子下的所有图片
     * @param articleId
     * @return
     */
    public List<String> getPictureListByArticleId(@Param("articleId") String articleId);

    /**
     * 获取指定用户有效帖子的总数
     * @param userId
     * @return
     */
    public Integer getAmountOfAvailableArticleListByUserId(@Param("userId")Integer userId);

    /**
     * 获取用户对指定帖子的点赞记录的id
     * @param articleId
     * @param userId
     * @return
     */
    public Integer getTheLikeFlagOfTheArticle(@Param("articleId")Integer articleId, @Param("userId")Integer userId);

    /**
     * 新增一条用户对指定帖子的点赞记录
     * @param articleId
     * @param userId
     * @return
     */
    public Integer insertNewLikeFlagOfTheArticle(@Param("articleId")Integer articleId, @Param("userId")Integer userId);

    /**
     * 删除指定id的帖子点赞记录
     * @param recodeId
     * @return
     */
    public Integer deleteOldLikeFlagOfTheArticle(@Param("recodeId")Integer recodeId);

    /**
     * 对指定帖子的点赞数量加一
     * @param articleId
     * @return
     */
    public Integer plusOneLikeAmountOfTheArticle(@Param("articleId")Integer articleId);

    /**
     * 对指定帖子的点赞数量减一
     * @param articleId
     * @return
     */
    public Integer minusOneLikeAmountOfTheArticle(@Param("articleId")Integer articleId);

    /**
     * 新增指定帖子的留言记录
     * @param articleId
     * @param userId
     * @param commentContent
     * @return
     */
    public Integer insertNewCommentOfTheArticle(@Param("articleId")Integer articleId, @Param("parentId")Integer parentId, @Param("userId")Integer userId, @Param("commentContent")String commentContent);

    /**
     * 删除帖子的指定留言记录
     * @param recodeId
     * @return
     */
    public Integer deleteCommentOfArticleById(@Param("recodeId")Integer recodeId);

    /**
     * 获取指定帖子的详情内容
     */
    public Map<String, Object> getArticleDetailsByArticleId(@Param("articleId")Integer articleId);

    /**
     * 获取指定帖子下的所有评论
     * @param articleId
     * @return
     */
    public List<Map<String, Object>> getArticleCommentByArticleId(@Param("articleId")Integer articleId);

    /**
     * 帖子的已阅数据量加一
     * @param articleId
     * @return
     */
    public Integer plusOneReadAmountOfTheArticle(@Param("articleId")Integer articleId);

    /**
     * 指定帖子的评价数量加一
     * @param articleId
     * @return
     */
    public Integer plusOneCommentAmountOfTheArticle(@Param("articleId")Integer articleId);

    /**
     * 指定帖子的评价数量减一
     * @param articleId
     * @return
     */
    public Integer minusOneCommentAmountOfTheArticle(@Param("articleId")Integer articleId);

    /**
     * 假删除指定帖子（刚学的）
     * @param articleId
     * @return
     */
    public Integer deleteTheArticleByArticleId(@Param("articleId")Integer articleId);

    /**
     * 获取指定评价的信息
     * @param commentId
     * @return
     */
    public Map<String, Object> getCommentInfByCommentId(@Param("commentId")Integer commentId);


    // public Integer deleteThePictureOfArticleByArticleId(@Param("articleId")Integer articleId);

    // public Integer deleteTheContentOfArticleByArticleId(@Param("articleId")Integer articleId);

    // public Integer deleteTheCommentOfArticleByArticleId(@Param("articleId")Integer articleId);

    /**
     * 获取所有可以展示的帖子，分页
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAvailableArticleList(Map param);

    Integer getTotalAmountOfArticle();



}
