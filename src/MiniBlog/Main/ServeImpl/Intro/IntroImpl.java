package MiniBlog.Main.ServeImpl.Intro;

import MiniBlog.Main.Dao.Intro.IntroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public Map<String, Object> getLastIntro() {
        List<Map<String, Object>> introList = dao.selectAllIntro();
        Map<String, Object> result;
        if (Objects.isNull(introList) || introList.size() == 0) {
            result = new HashMap<>();
            result.put("content", "");
        } else {
            result = introList.get(0);
        }
        return result;
    }



}
