package MiniBlog.Main.Controller.Front.Common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/"})
public class DefaultController {

    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "redirect:/WebContext/MiniBlog_Front/X_Index/index";
    }
}
