package MiniBlog.Main.Dao.Administrator;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AdministratorDao {

    public int insertNewAdmin(Map param);

    public Map queryById(int id);

    public Map queryByAccount(String account);
}
