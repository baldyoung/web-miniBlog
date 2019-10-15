package MiniBlog.Main.Controller.Back.Login;


import MiniBlog.Main.Serve.Administrator.AdministratorServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Back/X_Login")
public class BackLogin {
    @Autowired
    AdministratorServe serve;

    @RequestMapping(value="/login", method={GET})
    public String defaultMethod(HttpSession session){
        String result =  "/MiniBlog_Back/X_Login/login";
        if(null != session.getAttribute("admin")) result = "redirect:../X_RealTimeMonitoring/realTimeMonitoring";
        return result;
    }

    @RequestMapping(value="/loginCheck", method={POST})
    @ResponseBody
    public Map<String, String> loginCheck(@RequestBody Map<String, String> param, HttpSession session){

        Map<String, String> result =  serve.loginCheck(param);
        Map<String, String> temp = new HashMap<String, String>();
        if(result.get("result").equals("true")){
            session.setAttribute("admin", param.get("account"));
            session.setAttribute("roleId", result.get("roleId"));
            //session.setMaxInactiveInterval(60 * 30); //单位是秒
            temp.put("result", "true");
            temp.put("inf","../X_RealTimeMonitoring/realTimeMonitoring" );
        }else{
            temp.put("result", "false");
        }
        return temp;
    }

    @RequestMapping(value="/logout", method={POST})
    @ResponseBody
    public Map<String, String> logout(HttpSession session){
        if(null != session.getAttribute("admin")){
            session.removeAttribute("admin");
            session.removeAttribute("roleId");
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", "true");
        return result;
    }
}
