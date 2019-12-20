package MiniBlog.Main.Dao.Message;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MessageDao {

    List<Map<String, Object>> selectMessagePage(@Param("limitStart")Integer limitStart, @Param("limitSize")Integer limitSize);

    Integer insertMessage(@Param("userId")Integer userId, @Param("content")String content);

    Integer updateMessageForbidFlag(@Param("recordId")Integer recordId, @Param("newFlag")String newFlag);

}
