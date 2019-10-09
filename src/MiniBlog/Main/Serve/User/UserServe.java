package MiniBlog.Main.Serve.User;

import org.springframework.stereotype.Service;

import java.util.Map;

//@Service
public interface UserServe {

    public Map<String, String> addNewUser(Map map);

    public Map queryByAccount(String Account);

    public Map queryById(int Id);
}
