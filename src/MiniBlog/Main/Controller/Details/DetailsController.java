package MiniBlog.Main.Controller.Details;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_Details"})
public class DetailsController {


    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "redirect:/WebContext/MiniBlog_Front/X_Details/details";
    }

    @RequestMapping(value={"/details"}, method={GET})
    public String indexMethod(){
        return "MiniBlog_Front/X_Details/details";
    }



}
