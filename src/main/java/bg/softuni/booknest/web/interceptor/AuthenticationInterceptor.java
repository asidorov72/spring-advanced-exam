package bg.softuni.booknest.web.interceptor;

import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.model.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String USER_ID_ATTRIBUTE = "user_id";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();

        if (uri.startsWith("/admin")) {

            UserDto user = session != null
                    ? (UserDto) session.getAttribute("user")
                    : null;

            if (user == null || user.getRole() != UserRole.ADMIN) {
                response.sendRedirect("/404");
                return false;
            }

            return true;
        }

        if (session != null && session.getAttribute(USER_ID_ATTRIBUTE) != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}