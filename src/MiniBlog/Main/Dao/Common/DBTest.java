package MiniBlog.Main.Dao.Common;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;

import static java.lang.System.out;

public class DBTest {

    public static List<Map> queryAllTableFromDatabase(){
        out.println("init starting ...");
        List<Map> list = null;
        // 指定全局配置文件
        String resource = "Mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        }catch(IOException e){
            out.println("init get error:"+e.getMessage());
        }
        if(null != inputStream){
            // 构建sqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 获取sqlSession
            SqlSession sqlSession = sqlSessionFactory.openSession();
            list = sqlSession.selectList("TestMapper.queryAllTableFromDatabase");

        }
        return list;
    }

}
