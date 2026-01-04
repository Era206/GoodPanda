package goodpanda.filter;

import goodpanda.domain.Rider;
import goodpanda.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 12/12/24
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI();
        User user = (User) (nonNull(session) ? session.getAttribute("user") : null);

        boolean isPublicPath = path.startsWith(httpRequest.getContextPath() + "/login")
                || path.startsWith(httpRequest.getContextPath() + "/signup")
                || path.equals(httpRequest.getContextPath() + "/")
                || path.contains("/resources");

        if (isPublicPath) {
            chain.doFilter(request, response);

            return;
        }

        if (isNull(session) || isNull(user)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");

            return;
        }

//        if (path.startsWith("/admin") && user.getRoleNames().contains(ADMIN)) {
//                chain.doFilter(request, response);
//
//                return;
//            }

        if (path.startsWith("/user")) {
            handleUserPath(httpRequest, httpResponse, chain, user);

            return;
        } else if (path.startsWith("/restaurantAdmin")) {
            handleRestaurantAdminPath(httpRequest, httpResponse, chain, user);

            return;
        } else if (path.startsWith("/rider")) {
            handleRiderPath(session, httpRequest, httpResponse, chain, user);

            return;
        }

        chain.doFilter(request, response);
    }

    private void handleUserPath(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain,
                                Object user)
            throws IOException, ServletException {

        String[] parts = request.getRequestURI().split("/");

        if (parts.length > 2 && !parts[2].equals(String.valueOf(((User) user).getId()))) {
            response.sendRedirect(request.getContextPath() + "/login");

            return;
        }

        chain.doFilter(request, response);
    }

    private void handleRestaurantAdminPath(HttpServletRequest request,
                                           HttpServletResponse response,
                                           FilterChain chain,
                                           Object user)
            throws IOException, ServletException {

        String[] parts = request.getRequestURI().split("/");

        if (parts.length > 2) {
            Integer requestedId = Integer.parseInt(parts[2]);

            if (requestedId.equals(((User) user).getId()) && nonNull(((User) user).getRestaurant())) {
                chain.doFilter(request, response);

                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/user/" + ((User) user).getId());
    }

    private void handleRiderPath(HttpSession session,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain,
                                 Object user)
            throws IOException, ServletException {

        Object rider = nonNull(session) ? session.getAttribute("rider") : null;
        String[] parts = request.getRequestURI().split("/");

        if (parts.length > 2) {
            Integer riderId = Integer.parseInt(parts[2]);

            if (nonNull((Rider) rider) && Objects.equals(((Rider) rider).getId(), riderId)) {
                chain.doFilter(request, response);

                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/user/" + ((User) user).getId());
    }

    @Override
    public void destroy() {
    }
}
