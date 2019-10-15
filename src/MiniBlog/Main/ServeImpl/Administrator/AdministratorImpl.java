package MiniBlog.Main.ServeImpl.Administrator;

import MiniBlog.Main.Dao.Administrator.AdministratorDao;
import MiniBlog.Main.Serve.Administrator.AdministratorServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdministratorImpl implements AdministratorServe {
    @Autowired
    AdministratorDao dao;

    @Override
    public Map<String, String> loginCheck(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        /*
         *这里应该添加对 map内数据的格式检查...待完成
         */
        result.put("result", "true");
        Map<String, String>  adminInf = dao.queryByAccount(map.get("account"));
        if(null != adminInf && null != adminInf.get("password") && adminInf.get("password").equals(map.get("password"))){
            result.put("result", "true");
            result.put("roleId", adminInf.get("roleId"));
        }else{
            result.put("result", "false");
        }

        return result;
    }
}
