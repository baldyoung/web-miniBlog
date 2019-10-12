package MiniBlog.Main.Controller.Back.DataManagement;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/WebContext/MiniBlog_Back/X_DataManagement")
public class DataManagementController {


    @RequestMapping(value="/dataManagement", method={GET})
    public String defaultMethod(){
        return "/MiniBlog_Back/X_DataManagement/dataManagement";
    }


}
