package MiniBlog.Main.Controller.Front.Login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Login")
public class LoginController {


    @RequestMapping(value="/login", method={GET})
    public String registerPage(){
        return "MiniBlog_Front/X_Login/login";
    }



}
