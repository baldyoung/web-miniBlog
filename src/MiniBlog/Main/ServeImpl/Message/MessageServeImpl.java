package MiniBlog.Main.ServeImpl.Message;

import MiniBlog.Main.Common.response.ListDto;
import MiniBlog.Main.Dao.Message.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServeImpl {

    private static final Integer DEFAULT_PAGE_SIZE = 11;
    private static final Integer DEFAULT_PAGE_EXPECT_SIZE = 10;
    private static final String MESSAGE_FORBID_FLAG_YES = "yes";
    private static final String MESSAGE_FORBID_FLAG_NO = "no";


    @Autowired
    private MessageDao messageDao;

    public ListDto getMessagePageList(Integer pageNumber) {
        List<Map<String, Object>> list = messageDao.selectMessagePage(pageNumber * DEFAULT_PAGE_EXPECT_SIZE, DEFAULT_PAGE_SIZE);
        return new ListDto(list, DEFAULT_PAGE_EXPECT_SIZE);
    }

    public void addNewMessage(Integer userId, String content) {
        messageDao.insertMessage(userId, content);
    }

    public void setMessageForbid(Integer recordId) {
        messageDao.updateMessageForbidFlag(recordId, MESSAGE_FORBID_FLAG_YES);
    }

}
