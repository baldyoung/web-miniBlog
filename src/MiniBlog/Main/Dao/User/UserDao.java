package MiniBlog.Main.Dao.User;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {

    public int insertNewUser(Map map);

    public Map queryByAccount(String Account);

    public Map queryById(int id);
}
