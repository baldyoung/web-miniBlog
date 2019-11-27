package MiniBlog.Main.Dao.Intro;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IntroDao {

    public Integer createIntroWithUserId(@Param("userId")Integer userId, @Param("content")String content);

    public Map<String, Object> getIntroByUserId(@Param("userId")Integer userId);

    public Integer updateIntroByUserId(@Param("content")String content, @Param("userId")Integer userId);

}
