package MiniBlog.Main.ServeImpl.User;

import MiniBlog.Main.Dao.User.UserDao;
import MiniBlog.Main.Serve.User.UserServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServeImpl implements UserServe {
    @Autowired
    UserDao dao;

    public Map<String, String> addNewUser(Map map){
        Map result = new HashMap<String, String>();
        /*
        *这里应该添加对 map内数据的格式检查...待完成
         */
        result.put("result", "true");
        String account = map.get("account").toString();
        if(dao.queryByAccount(account) != null){
            result.put("result", "false");
            result.put("inf", account+"已存在");
        }else if(1 != dao.insertNewUser(map))
            result.put("result", "false");
        return result;
    }
    public Map queryByAccount(String Account){
        return dao.queryByAccount(Account);
    }

    public Map queryById(int Id){
        return dao.queryById(Id);
    }
}
