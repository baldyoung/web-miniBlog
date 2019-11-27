package MiniBlog.Main.ServeImpl.Intro;

import MiniBlog.Main.Dao.Intro.IntroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntroImpl {
    @Autowired
    IntroDao dao;

    public Map<String, Object> getIntroByUserId(Integer userId) {
        return dao.getIntroByUserId(userId);
    }

    public boolean updateIntroByUserId(Integer userId, String content) {
        return dao.updateIntroByUserId(content, userId) > 0;
    }




}
