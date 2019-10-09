package MiniBlog.Main.Dao.Common;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DBLinkTest {
    public List<Map> queryAllTableFromDatabase();
}
