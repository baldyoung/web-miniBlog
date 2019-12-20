package MiniBlog.Main.Controller.Front.Message;

import MiniBlog.Main.Common.response.ListDto;
import MiniBlog.Main.Common.response.Result;
import static MiniBlog.Main.Common.Utils.CommonUtil.*;

import MiniBlog.Main.ServeImpl.Message.MessageServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Objects;

import static MiniBlog.Main.Common.Enum.ResultErrorInf.*;
import static java.lang.System.out;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value={"/WebContext/MiniBlog_Front/X_Message"})
public class MessageController {

    @Autowired
    private MessageServeImpl messageServe;

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

    @RequestMapping(value = "messageList", method = {GET})
    @ResponseBody
    public Result getMessageList(@RequestParam("pageNumber")Integer pageNumber) {
        if (Objects.isNull(pageNumber) || pageNumber < 0) {
            return Result.fail(PARAM_IS_EMPTY);
        }
        ListDto data = messageServe.getMessagePageList(pageNumber);
        return Result.success(data);
    }

    @RequestMapping(value = "message", method = {PUT})
    @ResponseBody
    public Result addMessage(@RequestParam("content")String content, HttpSession session) {
        if (isAnyNullObject(content, session)) {
            return Result.fail(PARAM_IS_EMPTY);
        }
        Integer userId = getUserIdWithOutException(session);
        userId = (Objects.isNull(userId) ? 0 : userId);
        messageServe.addNewMessage(userId, content);
        return Result.success();
    }

    @RequestMapping(value = "message", method = {DELETE})
    @ResponseBody
    public Result deleteMessage(@RequestParam("recordId")Integer recordId, HttpSession session) {
        if (isAnyNullObject(recordId, session, session.getAttribute("userId"))) {
            return Result.fail(PARAM_IS_EMPTY);
        }
        messageServe.setMessageForbid(recordId);
        return Result.fail();
    }

}
