package MiniBlog.Main.Controller.Front.UserManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_UserManager"})
public class UserManagerController {

        @RequestMapping(method={GET})
        public String defaultMethod(){
            out.println("UserManagerController get request");
            return "redirect:/WebContext/MiniBlog_Front/X_UserManager/userManager";
        }


    @RequestMapping(value={"/UserManager"}, method=GET)
    public String indexMethod(){
        return "MiniBlog_Front/X_UserManager/userManager";
    }
}
