package goodpanda.helper;

import goodpanda.domain.Order;
import goodpanda.domain.Restaurant;
import goodpanda.domain.User;
import goodpanda.service.OrderService;
import goodpanda.service.RestaurantService;
import goodpanda.service.RoleService;
import goodpanda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @author sanjidaera
 * @since 27/11/24
 */
@Component
public class AdminHelper {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public void prepareAndSetupLists(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);

        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
    }
}
