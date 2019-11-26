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
    public Map<String, String> queryByAccount(String Account){
        return dao.queryByAccount(Account);
    }

    public Map<String, String> queryById(int Id){
        return dao.queryById(Id);
    }

    public Map<String, String>  loginCheck(Map<String, String> map){
        /*
         *这里应该添加对 map内数据的格式检查...待完成
         */
        Map<String, String> result = new HashMap<>();;
        Map<String, String>  userInf = dao.queryByAccount(map.get("account"));
        //System.out.println("检查的用户信息："+userInf.toString());
        if(null != userInf && null != userInf.get("password") && userInf.get("password").equals(map.get("password"))){
            result.put("result", "true");
            result.put("userAccount", userInf.get("account"));
            result.put("userId", userInf.get("id"));
        }else{
            result.put("result", "false");
        }
        return result;
    }
}
