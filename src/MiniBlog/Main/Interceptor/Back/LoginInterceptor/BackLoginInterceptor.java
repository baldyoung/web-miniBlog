package MiniBlog.Main.Interceptor.Back.LoginInterceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class BackLoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        Object obj = request.getSession().getAttribute("admin");
        if(obj==null) {
            System.out.println(request.getContextPath());
            response.sendRedirect(request.getContextPath()+"/WebContext/MiniBlog_Back/X_Login/login");
            return false;
        }
        return true;
    }
}
