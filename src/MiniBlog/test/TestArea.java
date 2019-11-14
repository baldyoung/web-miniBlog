package MiniBlog.test;
import MiniBlog.Main.Serve.User.UserServe;
import MiniBlog.Main.ServeImpl.Article.ArticleServeImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;


public class TestArea extends  UnitTest{
    @Autowired
    UserServe userServe;

    @Autowired
    ArticleServeImpl articleServe;

    @Test
    public void test1(){
        out.println(userServe.queryByAccount("user"));
    }

    @Test
    public void test2(){
        List<String> list = new LinkedList<>();
        for(int i=0; i<10; i++) {
            list.add("test.jpg");
            list.add("what.jpg");
        }
        out.println(articleServe.addNewImg("666", list));
    }

}
