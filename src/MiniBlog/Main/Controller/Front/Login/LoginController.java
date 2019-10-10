package MiniBlog.Main.Controller.Front.Login;

import javax.servlet.http.HttpSession;
import MiniBlog.Main.Serve.User.UserServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Login")
public class LoginController {

    @Autowired
    UserServe serve;

    @RequestMapping(value="/login", method={GET})
    public String loginPage(HttpSession session){
        String result;
        if(null == session.getAttribute("account"))
            result = "MiniBlog_Front/X_Login/login";
        else
            result =  "redirect:/WebContext/MiniBlog_Front/X_Management/management";
        return result;
    }

    @RequestMapping(value="/loginCheck", method={POST})
    @ResponseBody
    public Map<String, String> loginCheck(@RequestBody Map<String, String> param, HttpSession session){
        System.out.println("loginMsg:"+param.toString());
        Map<String, String> result =  serve.loginCheck(param);
        Map<String, String> temp = new HashMap<String, String>();
        if(result.get("result").equals("true")){
            session.setAttribute("account", param.get("account"));
            //session.setMaxInactiveInterval(60 * 30); //单位是秒
            temp.put("result", "true");
            temp.put("inf","../X_Management/management" );
        }else{
            temp.put("result", "false");
        }
        return temp;
    }




}
