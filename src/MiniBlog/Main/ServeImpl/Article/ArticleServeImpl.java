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
    // 默认的分页后的最大数据量
    static private Integer DEFAULT_PAGING_AMOUNT = 5;

    @Autowired
    ArticleDao dao;
    @Autowired
    WorkingPoolModule ImgWorkingPool;


    /**
     * 获取指定文章的详情内容
     * @param articleId
     * @param userId
     * @return
     */
    public Map<String, Object> getArticleDetailsByArticleId(Integer articleId, Integer userId) {
        Map<String, Object> result;
        result = dao.getArticleDetailsByArticleId(articleId);
        if (null != result) {
            // 获取对应的图片数据
            List<String> pictureList = dao.getPictureListByArticleId(articleId.toString());
            if (null != userId && null != dao.getTheLikeFlagOfTheArticle(articleId, userId)) {
                result.put("isLile", "yes");
            } else {
                result.put("isLike", "no");
            }
            result.put("pictureList", pictureList);
        }
        return result;
    }

    /**
     * 获取指定文章的评论列表
     * @param articleId
     * @return
     */
    public List<Map<String, Object>> getArticleCommentByArticleId(Integer articleId) {
        List<Map<String, Object>> result = dao.getArticleCommentByArticleId(articleId);
        return result;
    }

    /**
     * 用户发布article
     * @param param
     * @return
     */
    public Map<String, String> addNewArticle(Map<String, Object> param){
        Map<String, String> result = new HashMap<>();
        result.put("result", "false");
        // 新增一个article对象到数据库
        Integer newArticleId;
        if(1 == dao.insertNewArticle(param)) {
            // 重新设置新增的article对象的编号
            newArticleId = Integer.parseInt(param.get("id").toString());
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

    /**
     * 将给定图片集追加给指定帖子
     * @param articleId
     * @param list
     * @return
     */
    public int addNewImg(String articleId, List<String> list){
        return dao.insertNewImgList(articleId, list);
    }


    /**
     *  #{userId} LIMIT #{firstIndex}, #{maxAmount};
     *  获取指定用户有效的已发文章/帖子，所谓有效即没有被封禁的文章/帖子
     *  map中的参数需求{userId:要查询的用户Id, firstIndex:分页参数-第一条数据的小标, maxAmount:分页参数-最多几条数据}
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAvailableArticleListByUserId(Map<String, Object> param) {
        List<Map<String, Object>> result ;
        Integer userId = Integer.parseInt(param.get("userId").toString());
        // 获取article表中关于帖子的主要字段
        result = dao.getAvailableArticleListByUserId(param);
        // 获取每个帖子的文字详情
        if (null != result) {
            for (Map<String, Object> temp : result) {
                List<String> pictureList = dao.getPictureListByArticleId(temp.get("id").toString());
                if (null != dao.getTheLikeFlagOfTheArticle(Integer.parseInt(temp.get("id").toString()), userId)) {
                    temp.put("isLile", "yes");
                } else {
                    temp.put("isLike", "no");
                }
                temp.put("pictureList", pictureList);
            }
        }
        return result;
    }

    /**
     * 获取指定用户有效帖子的总数
     * @param userId
     * @return
     */
    public Map<String, Object> getAmountOfAvailableArticleListByUserId(Integer userId) {
        Integer amount =  dao.getAmountOfAvailableArticleListByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", amount);
        result.put("maxDisplayAmount", DEFAULT_PAGING_AMOUNT);
        return result;
    }

    /**
     * 用户对帖子点赞和取消点赞的操作
     * @param articleId
     * @param userId
     * @return
     */
    public Map<String, Object> markLikeForArticle(Integer articleId, Integer userId) {
        Map<String, Object> result = new HashMap<>();
        Integer recodeId = dao.getTheLikeFlagOfTheArticle(articleId, userId);
        if (null == recodeId) {
            // 用户没有对这个帖子点赞，执行点赞操作
            recodeId = dao.insertNewLikeFlagOfTheArticle(articleId, userId);
            // 对应帖子的点赞数量加一
            dao.plusOneLikeAmountOfTheArticle(articleId);
            result.put("status", "like" );
        } else {
            // 用户已经对这个点过赞，执行取消点赞操作
            recodeId = dao.deleteOldLikeFlagOfTheArticle(recodeId);
            // 对应帖子的点赞数量减一
            dao.minusOneLikeAmountOfTheArticle(articleId);
            result.put("status", "unlike");
        }
        result.put("result", recodeId == 1 ? "success" : "fail");
        return result;
    }

    /**
     * 用户对帖子进行留言的服务
     * @param articleId
     * @param userId
     * @param commentContent
     * @return
     */
    public Map<String, Object> addCommentAboutArticle(Integer articleId, Integer userId, String commentContent) {
        Map<String, Object> result = new HashMap<>();
        if (dao.insertNewCommentOfTheArticle(articleId, userId, commentContent) > 0) {
            result.put("result", "success");
        } else {
            result.put("result", "success");
        }
        return result;
    }

    /**
     * 删除用户对帖子的留言的服务
     * @param recodeId
     * @return
     */
    public Map<String, Object> deleteCommentAboutArticle(Integer recodeId) {
        Map<String, Object> result = new HashMap<>();
        if (dao.deleteCommentOfArticleById(recodeId) > 0) {
            result.put("result", "success");
        } else {
            result.put("result", "success");
        }
        return result;
    }


}
