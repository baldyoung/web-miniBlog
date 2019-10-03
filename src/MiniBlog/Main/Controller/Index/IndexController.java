package MiniBlog.Main.Controller.Index;

import MiniBlog.Main.Dao.Common.DBTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static java.lang.System.out;
@Controller
@RequestMapping(value="/MiniBlog_Front/index")
public class IndexController {



    @RequestMapping(value="/", method={GET})
    public String defaultMethod(){

        out.println("indexController get request");

        return "MiniBlog_Front/index";
    }

    //测试数据库连接是否可行
    @RequestMapping(value={"/testDBLink"}, method={GET, POST})
    @ResponseBody
    public String testMethod(){
        return DBTest.queryAllTableFromDatabase().toString();
    }

}
