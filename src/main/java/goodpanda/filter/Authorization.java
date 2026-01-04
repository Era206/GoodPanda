package goodpanda.filter;

import goodpanda.domain.User;
import goodpanda.domain.UserRole;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 12/12/24
 */
@Component
public class Authorization {

    public boolean checkAuthorization(ServletRequest request, UserRole userRole) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI();
        User user = (User) (nonNull(session) ? session.getAttribute("user") : null);

        return path.startsWith("/admin") && user.getRoleNames().contains(userRole);
    }
}