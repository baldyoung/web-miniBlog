package MiniBlog.Main.Controller.Front.Comment;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/comment"})
public class CommentController {


    @RequestMapping(method={GET})
    public String defaultMethod(){
        return "MiniBlog_Front/X_Details/comment";
    }
}
