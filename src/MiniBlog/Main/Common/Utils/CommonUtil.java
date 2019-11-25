package MiniBlog.Main.Common.Utils;

public class CommonUtil {


    public static boolean isAnyNullObject(Object... args) {
        for(Object temp : args) {
            if (null == temp) return true;
        }
        return false;
    }
}
