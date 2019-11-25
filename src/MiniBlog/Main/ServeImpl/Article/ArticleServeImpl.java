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
        List<Map<String, Object>> result = new LinkedList<>();
        // 获取article表中关于帖子的主要字段
        result = dao.getAvailableArticleListByUserId(param);
        // 获取每个帖子的文字详情
        for(Map<String, Object> temp : result) {
            List<String> pictureList = dao.getPictureListByArticleId(temp.get("id").toString());
            temp.put("pictureList", pictureList);
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

    public Map<String, Object> markLikeForArticle(Integer articleId) {
        Map<String, Object> result = new HashMap<>();

        return result;
    }

}
