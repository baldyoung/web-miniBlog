package MiniBlog.Main.Controller.Front.About;


import MiniBlog.Main.Common.Result;
import MiniBlog.Main.ServeImpl.Intro.IntroImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_About"})
public class AboutController {

    @Autowired
    IntroImpl serve;

    @RequestMapping(method={GET})
    public String defaultMethod(){
        out.println("aboutController get request");
        return "redirect:/WebContext/MiniBlog_Front/X_About/about";
    }

    @RequestMapping(value={"/about"}, method=GET)
    public String indexMethod(){
        return "MiniBlog_Front/X_About/about";
    }

    @RequestMapping("/getIntro")
    @ResponseBody
    public Result getIntro(@RequestParam("id")Integer userId) {
        Map<String, Object> result = serve.getIntroByUserId(userId);
        if (null != result) {
            return Result.success(result);
        }
        return Result.fail();
    }

}
