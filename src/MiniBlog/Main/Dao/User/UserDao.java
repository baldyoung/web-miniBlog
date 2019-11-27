package MiniBlog.Main.Dao.User;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {

    public int insertNewUser(Map map);

    public Map queryByAccount(String Account);

    public Map queryById(int id);

    public Map<String, Object> getUserInfByUserId(@Param("userId")Integer userId);

    public String getUserPictureByUserId(@Param("userId")Integer userId);

    public Integer createUserPictureByUserId(@Param("userId")Integer userId, @Param("userPicture")String userPicture);

}
