package MiniBlog.Main.Controller.Details;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/details"})
public class DetailsController {


    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "MiniBlog_Front/X_Details/details";
    }
}
