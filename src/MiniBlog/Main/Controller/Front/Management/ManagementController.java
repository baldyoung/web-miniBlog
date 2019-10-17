package MiniBlog.Main.Controller.Front.Management;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Management")
public class ManagementController {

    @RequestMapping(value="/management", method={GET})
    public String defaultMethod(){
        return "MiniBlog_Front/X_Management/managementX";
    }


}
