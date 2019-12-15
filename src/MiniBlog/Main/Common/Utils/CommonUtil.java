package MiniBlog.Main.Common.Utils;

import MiniBlog.Main.Common.Exception.ServeException;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class CommonUtil {

    public static Integer getUserId(HttpSession session) throws ServeException {
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("userId"))) {
            throw new ServeException();
        }
        return null;
    }

    public static boolean isAnyNullObject(Object... args) {
        for(Object temp : args) {
            if (null == temp) return true;
        }
        return false;
    }
}
