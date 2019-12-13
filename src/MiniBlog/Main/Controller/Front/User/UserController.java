package MiniBlog.Main.Controller.Front.User;

import MiniBlog.Main.Serve.User.UserServe;
import MiniBlog.Main.ServeImpl.User.UserServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/User")
public class UserController {

    @RequestMapping(value="/register", method={GET})
    public String registerPage(){
        return "MiniBlog_Front/X_Register/register";
    }
    @RequestMapping(value="/login", method={GET})
    public String loginPage(){
        return "MiniBlog_Front/X_Login/login";
    }

    @Autowired
    private UserServeImpl serve;


}
