package MiniBlog.Main.Controller.Back.Login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Back/X_Login")
public class BackLogin {

    @RequestMapping(value="/login", method={GET})
    public String defaultMethod(){
        return "/MiniBlog_Back/X_Login/login";
    }
}
