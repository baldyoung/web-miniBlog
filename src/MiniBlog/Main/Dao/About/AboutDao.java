package MiniBlog.Main.Dao.About;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutDao {

    Integer insertNewAbout(@Param("userId")Integer userId, @Param("content")String content);

    Integer updateTheTargetAbout(@Param("aboutId")Integer aboutId, @Param("userId")Integer userId, @Param("content")String content);

    String selectTheTargetAbout(@Param("aboutId")Integer aboutId);

}
