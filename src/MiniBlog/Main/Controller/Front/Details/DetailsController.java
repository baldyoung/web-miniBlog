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
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping("getArticleDetails")
    @ResponseBody
    public Result getArticleDetails(@RequestParam("articleId")Integer articleId, HttpSession session) {
        Integer userId = (null != session && null != session.getAttribute("userId")) ? Integer.parseInt(session.getAttribute("userId").toString()) : null;
        Map<String, Object> data = serve.getArticleDetailsByArticleId(articleId, userId);
        if (null != data) {
            List<Map<String, Object>> commentList = serve.getArticleCommentByArticleId(articleId);
            if (null != commentList) {
                for (Map<String, Object>cell : commentList) {
                    if (StringUtils.equals(cell.get("userId").toString(), session.getAttribute("userId").toString())) {
                        cell.put("isOwner", "yes");
                    } else {
                        cell.put("isOwner", "no");
                    }
                }
            }
            data.put("commentList", commentList);
            return Result.success(data);
        }
        return Result.fail();
    }

    @RequestMapping("getCommentListOfArticle")
    @ResponseBody
    public Result getCommentListOfArticle(@RequestParam("articleId")Integer articleId, HttpSession session) {
        List<Map<String, Object>> commentList = serve.getArticleCommentByArticleId(articleId);
        if (null != commentList) {
            for (Map<String, Object>cell : commentList) {
                if (StringUtils.equals(cell.get("userId").toString(), session.getAttribute("userId").toString())) {
                    cell.put("isOwner", "yes");
                } else {
                    cell.put("isOwner", "no");
                }
            }
            return Result.success(commentList);
        }
        return Result.fail();
    }

    /**
     * 用户对帖子的留言操作
     * @param articleId
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping("addCommentAboutArticle")
    @ResponseBody
    public Result addCommentAboutArticle(@RequestParam("articleId")Integer articleId, @RequestParam("comment")String comment, HttpSession session) {
        Map<String, Object> result = serve.addCommentAboutArticle(articleId, Integer.parseInt(session.getAttribute("userId").toString()), comment);
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
    @RequestMapping("deleteCommentAboutArticle")
    @ResponseBody
    public Result deleteCommentAboutArticle(@RequestParam("commentId")Integer commentId) {
        Map<String, Object> result = serve.deleteCommentAboutArticle(commentId);
        if (StringUtils.equals("success", result.get("result").toString())) {
            return Result.success();
        }
        return Result.fail();
    }

}
