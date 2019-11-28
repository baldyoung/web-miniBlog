package MiniBlog.Main.Controller.Front.HomePage;


import MiniBlog.Main.Common.Enum.ResultErrorInf;
import MiniBlog.Main.Common.Result;
import MiniBlog.Main.Common.Utils.CommonUtil;
import MiniBlog.Main.ServeImpl.Article.ArticleServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/G_HomePage")
public class HomePageController {
    private static final Integer DEFAULT_MAX_DISPLAY_NUMBER_IN_PAGING = 5;


    @Autowired
    ArticleServeImpl serve;

    @RequestMapping( method={GET})
    public String defaultMethod(){
        return "redirect:/WebContext/MiniBlog_Front/G_HomePage/homePage";
    }

    @RequestMapping(value={"/homePage"}, method=GET)
    public String indexMethod(){
        out.println("HomePageController get message");
        return "MiniBlog_Front/G_HomePage/homePage";
    }

    @RequestMapping("/getArticleList")
    @ResponseBody
    public Result getArticleList(@RequestParam(value="firstIndex")Integer firstIndex, HttpSession session) {
        Integer maxAmount = DEFAULT_MAX_DISPLAY_NUMBER_IN_PAGING;
        Integer userId = null;
        if (null != session && null != session.getAttribute("userId")) {
            userId = Integer.parseInt(session.getAttribute("userId").toString());
        }
        List<Map<String, Object>> data;
        // 参数为空检查
        if (CommonUtil.isAnyNullObject(firstIndex, maxAmount)) {
            return Result.fail(ResultErrorInf.PARAM_IS_EMPTY);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("firstIndex", firstIndex);
        param.put("maxAmount", maxAmount);
        data = serve.getAvailableArticleList(param);
        if (null == data) {
            return Result.fail(ResultErrorInf.DATA_REQUEST_FAIL);
        }
        return Result.success(data);
    }

    @RequestMapping("/getInitPagingData")
    @ResponseBody
    public Result getInitDataOfPaging() {
        Map<String, Object> result = serve.getAllPublishArticlePagingData();
        if (null != result) {
            return Result.success(result);
        }
        return Result.fail();
    }
}
