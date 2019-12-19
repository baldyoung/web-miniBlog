package MiniBlog.Main.ServeImpl.About;

import MiniBlog.Main.Dao.About.AboutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutServeImpl {
    @Autowired
    private AboutDao aboutDao;

    public String getTheFirstAboutContent() {
        String content = aboutDao.selectTheTargetAbout(1);
        return content;
    }

    public void updateTheAboutContent(Integer userId, String content) {
        aboutDao.insertNewAbout(userId, content);
        aboutDao.updateTheTargetAbout(1, userId, content);
    }

}
