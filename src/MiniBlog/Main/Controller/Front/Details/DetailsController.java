package MiniBlog.Main.Controller.Front.Details;


import MiniBlog.Main.Common.Result;
import MiniBlog.Main.ServeImpl.Article.ArticleServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import static MiniBlog.Main.Common.Utils.CommonUtil.*;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_Details"})
public class DetailsController {



    @Autowired
    ArticleServeImpl serve;

    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "redirect:/WebContext/MiniBlog_Front/X_Details/details";
    }

    @RequestMapping(value={"/details"}, method={GET})
    public String indexMethod(){
        return "MiniBlog_Front/X_Details/details";
    }

    /**
     * 获取帖子的详情内容（包括评论数据）
     * @param articleId
     * @param session
     * @return
     */
    @RequestMapping("/getArticleDetails")
    @ResponseBody
    public Result getArticleDetails(@RequestParam("articleId")Integer articleId, HttpSession session) {
        Integer userId = null;
        if (!Objects.isNull(session.getAttribute("userId"))) {
            userId = Integer.parseInt(session.getAttribute("userId").toString());
        }
        Map<String, Object> data = serve.getArticleDetailsByArticleId(articleId, userId);
        if (!Objects.isNull(data)) {
            List<Map<String, Object>> commentList = serve.getArticleCommentByArticleId(articleId);
            if (!Objects.isNull(commentList)) {
                for (Map<String, Object>cell : commentList) {
                    if (Objects.isNull(userId)) {
                        cell.put("isOwner", "no");
                    } else {
                        cell.put("isOwner", "yes");
                    }
                }
            } else {
                commentList = new LinkedList<>();
            }
            data.put("commentList", commentList);
            return Result.success(data);
        }
        return Result.fail();
    }

    /**
     * 获取帖子下的评论集合
     *
     * @param articleId
     * @param session
     * @return
     */
    @RequestMapping("/getCommentListOfArticle")
    @ResponseBody
    public Result getCommentListOfArticle(@RequestParam("articleId")Integer articleId, HttpSession session) {

        Integer userId = Objects.isNull(session.getAttribute("userId")) ? null : Integer.parseInt(session.getAttribute("userId").toString());
        List<Map<String, Object>> commentList = serve.getArticleCommentByArticleId(articleId);
        if (!Objects.isNull(commentList)) {
            for (Map<String, Object>cell : commentList) {
                if (Objects.isNull(userId)) {
                    cell.put("isOwner", "no");
                } else {
                    cell.put("isOwner", "yes");
                }
            }
        } else {
            commentList = new LinkedList<>();
        }
        return Result.success(commentList);
    }

    /**
     * 用户对帖子的留言操作
     * @param articleId
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping("/addCommentAboutArticle")
    @ResponseBody
    public Result addCommentAboutArticle(@RequestParam("articleId")Integer articleId, @RequestParam(value = "parentId", defaultValue = "-1")Integer parentId, @RequestParam("comment")String comment, HttpSession session) {
        Integer userId = null;
        if (!Objects.isNull(session.getAttribute("userId"))) {
            userId = Integer.parseInt(session.getAttribute("userId").toString());
        }
        Map<String, Object> result = serve.addCommentAboutArticle(articleId, parentId, userId, comment);
        if (StringUtils.equals("success", result.get("result").toString())) {
            return Result.success();
        }
        return Result.fail();
    }

    /**
     * 用户对帖子留言的删除操作
     * @param commentId
     * @return
     */
    @RequestMapping("/deleteCommentAboutArticle")
    @ResponseBody
    public Result deleteCommentAboutArticle(@RequestParam("commentId")Integer commentId) {
        Map<String, Object> result = serve.deleteCommentAboutArticle(commentId);
        if (StringUtils.equals("success", result.get("result").toString())) {
            return Result.success();
        }
        return Result.fail();
    }

    /**
     * 帖子点赞（支持非登录状态）
     * @param articleId
     * @param session
     * @return
     */
    @RequestMapping("/markLikeFlagOfArticle")
    @ResponseBody
    public Result markLikeFlagOfArticle(@RequestParam("articleId")Integer articleId, HttpSession session) {
        Integer userId = getUserIdWithOutException(session);
        Map<String, Object> data = serve.markLikeForArticleWithOutUserId(articleId, userId);
        if (serveResultIsSuccess(data)) {
            return Result.success(data);
        }
        return Result.fail();
    }

}
