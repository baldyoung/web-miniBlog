package MiniBlog.test;
import MiniBlog.Main.Serve.User.UserServe;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static java.lang.System.out;


public class TestArea {
    @Autowired
    UserServe userServe;

    @Test
    public void test1(){
        out.println(userServe.queryByAccount("admin"));
    }

}
