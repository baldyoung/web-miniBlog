package MiniBlog.Main.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexDefaultController {
    @RequestMapping()
    public String defaultMethod(){
        //return "redirect:/WebContext/MiniBlog_Front/G_HomePage/homePage";
        return "redirect:/WebContext/MiniBlog_Front/X_Index/index";
    }
}
