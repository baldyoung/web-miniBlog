package MiniBlog.Main.Controller.Front.Register;

import MiniBlog.Main.Serve.User.UserServe;
import MiniBlog.Main.ServeImpl.User.UserServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Register")
public class RegisterController {

    @Autowired
    private UserServeImpl serve;

    @RequestMapping(value="/register", method={GET})
    public String registerPage(){
        return "MiniBlog_Front/X_Register/register";
    }

    @RequestMapping(value="/registerNewUser", method={POST})
    @ResponseBody
    public Map<String, String> registerNewUser(@RequestBody Map<String, String> param){
        System.out.println("registerNewUser get msg:"+param.toString());
        return serve.addNewUser(param);
    }

}
