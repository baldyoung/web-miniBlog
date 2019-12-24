package MiniBlog.Main.Dao.User;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {

    int insertNewUser(Map map);

    Map queryByAccount(String Account);

    Map queryById(int id);

    Map<String, Object> getUserInfByUserId(@Param("userId")Integer userId);

    String getUserPictureByUserId(@Param("userId")Integer userId);

    Integer createUserPictureByUserId(@Param("userId")Integer userId, @Param("userPicture")String userPicture);

    Map<String, Object> selectUserById(@Param("userId")Integer userId);

    String selectUserPictureByUserId(@Param("userId")Integer userId);

    Integer updateUserById(Map map);

    Integer updateUserPictureByUserId(Map map);

}
