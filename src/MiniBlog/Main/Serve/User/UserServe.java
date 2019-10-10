package MiniBlog.Main.Serve.User;

import org.springframework.stereotype.Service;

import java.util.Map;

//@Service
public interface UserServe {

    public Map<String, String> addNewUser(Map map);

    public Map<String, String> queryByAccount(String Account);

    public Map<String, String> queryById(int Id);

    public Map<String, String>  loginCheck(Map<String, String> map);
}
