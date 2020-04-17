package MiniBlog.Main.Controller.Front.Register;

import MiniBlog.Main.Common.response.Result;
import MiniBlog.Main.Serve.User.UserServe;
import MiniBlog.Main.ServeImpl.User.UserServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static MiniBlog.Main.Common.Enum.ResultErrorInf.PARAM_IS_EMPTY;
import static MiniBlog.Main.Common.Utils.CommonUtil.getUserIdWithOutException;
import static MiniBlog.Main.Common.Utils.CommonUtil.isAnyNullObject;
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
    public Map<String, String> registerNewUser(@RequestBody Map<String, String> param, HttpSession session){
        Integer userId = getUserIdWithOutException(session);
        if (null == userId) {
            return null;
        }
        if (0 == userId.intValue()) {
            Map<String, String> map = new HashMap();
            map.put("result", "false");
            map.put("inf", "权限不足");
            return map;
        }
        System.out.println("registerNewUser get msg:"+param.toString());
        return serve.addNewUser(param);
    }

}
