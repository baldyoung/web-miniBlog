package MiniBlog.Main.Controller.Common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/"})
public class DefaultController {

    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "redirect:/WebContext/MiniBlog_Front/X_Index/index";
    }
}
