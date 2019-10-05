package MiniBlog.Main.Controller.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_Message"})
public class MessageController {

    @RequestMapping(method={GET})
    public String defaultMethod(){
        out.println("messageController get request");
        return "redirect:/WebContext/MiniBlog_Front/X_Message/message";
    }

    @RequestMapping(value={"/message"}, method={GET})
    public String indexMethod(){
        out.println("messageController get request");
        return "MiniBlog_Front/X_Message/message";
    }
}
