package MiniBlog.Main.Controller.Back.UserManagement;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Back/X_UserManagement")
public class UserManagementController {


    @RequestMapping(value="/userManagement", method={GET})
    public String defaultMethod(){
        return "/MiniBlog_Back/X_UserManagement/userManagement";
    }

}
