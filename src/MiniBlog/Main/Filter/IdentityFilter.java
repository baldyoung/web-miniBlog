package MiniBlog.Main.Filter;


import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 监管游客身份，负责对游客身份进行标识
 * @author baldyoung
 */
public class IdentityFilter extends OncePerRequestFilter {
    private static final Integer DEFAULT_VISITOR_ID = 333;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (false && Objects.isNull(session.getAttribute("userId"))) {
            session.setAttribute("userId", DEFAULT_VISITOR_ID);
        }
        filterChain.doFilter(request, response);
    }

}
