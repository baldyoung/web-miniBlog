package MiniBlog.Main.Controller.Front.About;


import MiniBlog.Main.Common.Result;
import MiniBlog.Main.Common.Utils.CommonUtil;
import MiniBlog.Main.ServeImpl.About.AboutServeImpl;
import MiniBlog.Main.ServeImpl.Intro.IntroImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_About"})
public class AboutController {

    @Autowired
    AboutServeImpl aboutServe;

    @RequestMapping(method={GET})
    public String defaultMethod(){
        out.println("aboutController get request");
        return "redirect:/WebContext/MiniBlog_Front/X_About/about";
    }

    @RequestMapping(value={"/about"}, method=GET)
    public String indexMethod(){
        return "MiniBlog_Front/X_About/about";
    }

    @RequestMapping("/getAbout")
    @ResponseBody
    public Result getAbout(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String content = aboutServe.getTheFirstAboutContent();
        if (Objects.isNull(content)) {
            content = "";
        }
        result.put("content", content);
        String isOwner = "yes";
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("userId"))) {
            isOwner = "no";
        }
        result.put("isOwner", isOwner);
        return Result.success(result);
    }

    @RequestMapping("/updateAbout")
    @ResponseBody
    public Result updateAbout(@RequestParam("content")String content, HttpSession session) {
        Integer userId = CommonUtil.getUserIdWithOutException(session);
        if (Objects.isNull(userId)) {
            return Result.fail();
        }
        aboutServe.updateTheAboutContent(userId, content);
        return Result.success();
    }

}
