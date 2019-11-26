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


    public Result getArticleDetails(@RequestParam("articleId")Integer articleId) {

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
     * @param recodeId
     * @return
     */
    @RequestMapping("deleteCommentAboutArticle")
    @ResponseBody
    public Result deleteCommentAboutArticle(@RequestParam("recodeId")Integer recodeId) {
        Map<String, Object> result = serve.deleteCommentAboutArticle(recodeId);
        if (StringUtils.equals("success", result.get("result").toString())) {
            return Result.success();
        }
        return Result.fail();
    }

}
