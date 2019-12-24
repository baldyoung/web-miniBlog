package MiniBlog.Main.Common.Utils;

import MiniBlog.Main.Common.Exception.ServeException;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

import static MiniBlog.Main.Common.Enum.CommonEnum.*;

public class CommonUtil {

    public static Integer getUserId(HttpSession session) throws ServeException {
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("userId"))) {
            throw new ServeException();
        }
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        return userId;
    }

    public static Integer getUserIdWithOutException(HttpSession session) {
        Integer userId = null;
        try {
            userId = getUserId(session);
        } catch (Exception e) {

        }
        return userId;
    }

    public static boolean isAnyNullObject(Object... args) {
        if (Objects.isNull(args)) {
            return true;
        }
        for(Object temp : args) {
            if (null == temp) {
                return true;
            }
        }
        return false;
    }

    public static boolean serveResultIsSuccess(Map<String, Object> result) {
        return StringUtils.equals(RESULT_STATUS_SUCCESS, result.get(RESULT_STATUS));
    }
}
