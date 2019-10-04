package MiniBlog.Main.Controller.About;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/about"})
public class AboutController {

    @RequestMapping(method={GET})
    public String defaultMethod(){
        out.println("aboutController get request");
        return "MiniBlog_Front/about";
    }
}
