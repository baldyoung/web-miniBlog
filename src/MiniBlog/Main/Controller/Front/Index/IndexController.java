package MiniBlog.Main.Controller.Front.Index;

import MiniBlog.Main.Dao.Common.DBLinkTest;
import MiniBlog.Main.Serve.User.UserServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static java.lang.System.out;
@Controller
@RequestMapping(value="/WebContext/MiniBlog_Front/X_Index")
public class IndexController {

    @Autowired
    DBLinkTest dbtest;
    @Autowired
    private UserServe serve;


    @RequestMapping( method={GET})
    public String defaultMethod(){
        out.println("indexController get request");
        return "redirect:/WebContext/MiniBlog_Front/X_Index/index";
    }

    @RequestMapping(value={"/index"}, method=GET)
    public String indexMethod(){
        return "MiniBlog_Front/X_Index/index";
    }

    //测试数据库连接是否可行
    @RequestMapping(value={"/testDBLink"}, method={GET, POST})
    @ResponseBody
    public String testMethod(){
        return dbtest.queryAllTableFromDatabase().toString();
    }

    @RequestMapping(value={"/insertDB"}, method={GET, POST})
    @ResponseBody
    public String testMethod2() {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("account", "admin3");
        temp.put("password", "pwd");
        //temp.put("sex", "男");
        temp.put("mailbox", "1234567@qq.com");
        return ""+serve.addNewUser(temp);


    }


}
