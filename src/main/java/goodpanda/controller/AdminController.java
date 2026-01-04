package goodpanda.controller;

import goodpanda.domain.*;
import goodpanda.propertyEditor.*;
import goodpanda.service.*;
import goodpanda.domain.*;
import goodpanda.filter.Authorization;
import goodpanda.helper.AdminHelper;
import goodpanda.propertyEditor.*;
import goodpanda.service.*;
import goodpanda.validator.nidValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static goodpanda.domain.UserRole.ADMIN;

/**
 * @author sanjidaera
 * @since 27/11/24
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes("restaurant")
public class AdminController {

    @Autowired
    private AdminHelper adminHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private Authorization authorization;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

        webDataBinder.registerCustomEditor(List.class, "restaurantAdmins",
                new RestaurantAdminListEditor(userService));

        webDataBinder.registerCustomEditor(List.class, "locations",
                new LocationListEditor(locationService));

        webDataBinder.registerCustomEditor(List.class, "categories",
                new CategoryListEditor(categoryService));
    }

    @InitBinder("user")
    public void initBinderUser(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new nidValidator(userService));
    }

    @InitBinder("rider")
    public void initBinderRider(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(User.class, new UserEditor(userService));
        webDataBinder.registerCustomEditor(Location.class, new LocationEditor(locationService));
    }

    @GetMapping
    public String adminDashboard(ModelMap model,
                                 ServletRequest servletRequest) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            adminHelper.prepareAndSetupLists(model);
            List<Rider> riders = riderService.getAllRiders();
            model.put("riders", riders);

            return "adminDashboard";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/addRestaurant")
    public String addRestaurant(ModelMap model,
                                ServletRequest servletRequest) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            model.put("restaurant", new Restaurant());
            model.put("users", userService.getAllRestaurantAdminCandidates());
            model.put("locations", locationService.getAllLocations());

            return "addRestaurant";
        }

        model.put("user", new User());

        return "login";
    }

    @PostMapping("/addRestaurant")
    public String addNewRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                   BindingResult bindingResult,
                                   ServletRequest servletRequest,
                                   ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            if (bindingResult.hasErrors()) {
                model.put("users", userService.getAllRestaurantAdminCandidates());
                model.put("locations", locationService.getAllLocations());

                return "addRestaurant";
            }

            restaurantService.saveOrUpdateRestaurant(restaurant);

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/modifyRestaurant/{id}")
    public String updateRestaurant(ModelMap model,
                                   ServletRequest servletRequest,
                                   @PathVariable("id") Integer id) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            model.put("restaurant", restaurant);
            List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
            List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);

            model.put("completedOrders", completedOrders);
            model.put("runningOrders", runningOrders);
            model.put("locations", locationService.getAllLocations());
            model.put("selectedLocations", restaurantService.getRestaurantById(id)
                    .getLocations().stream()
                    .map(Location::getId)
                    .collect(toList()));

            return "modifyRestaurant";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant(@PathVariable("id") int id,
                                   ServletRequest servletRequest,
                                   ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            restaurantService.deleteRestaurantById(id);
            adminHelper.prepareAndSetupLists(model);

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("modifyRestaurant/{id}/addFood")
    public String addFood(ModelMap model, ServletRequest servletRequest) {
        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            model.put("food", new Food());
            model.put("categories", categoryService.getAllCategories());

            return "restaurantAddFoodForm";
        }

        model.put("user", new User());

        return "login";
    }

    @PostMapping("modifyRestaurant/{id}/addFood")
    public String addFood(@Valid @ModelAttribute("food") Food food,
                          BindingResult bindingResult,
                          @PathVariable("id") Integer id,
                          ServletRequest servletRequest,
                          ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            if (bindingResult.hasErrors()) {
                model.put("categories", categoryService.getAllCategories());

                return "restaurantAddFoodForm";
            }

            food.setId(null);
            Restaurant restaurant = restaurantService.getRestaurantById(id);

            food.setRestaurant(restaurant);
            foodService.saveOrUpdateFood(food);
            restaurantService.saveOrUpdateRestaurant(restaurant);

            return "redirect:/admin/modifyRestaurant/{id}";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Integer orderId,
                              ServletRequest servletRequest,
                              ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            Order order = orderService.getOrderById(orderId);

            if (nonNull(order)) {
                Restaurant restaurant = order.getRestaurant();

                if (isNull(restaurant)) {
                    return "redirect:/admin";
                }

                if (order.getStatus() != OrderStatus.DELIVERED) {
                    order.setStatus(OrderStatus.CANCELLED);
                    orderService.saveOrUpdateOrder(order);
                    List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
                    List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);

                    model.put("completedOrders", completedOrders);
                    model.put("runningOrders", runningOrders);

                    return "redirect:/admin/modifyRestaurant/" + restaurant.getId();
                } else {
                    return "redirect:/admin/modifyRestaurant/" + restaurant.getId();
                }
            }

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @PostMapping("/modifyRestaurant/{id}")
    public String updateRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                   BindingResult bindingResult,
                                   ServletRequest servletRequest,
                                   ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            if (bindingResult.hasErrors()) {
                model.put("locations", locationService.getAllLocations());

                return "modifyRestaurant";
            }

            restaurantService.saveOrUpdateRestaurant(restaurant);

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/addUser")
    public String addUser(ModelMap model, ServletRequest servletRequest) {
        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            User user = new User();
            model.put("user", user);

            return "addUser";
        }

        model.put("user", new User());

        return "login";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") User user,
                          BindingResult bindingResult,
                          ServletRequest servletRequest,
                          ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            if (bindingResult.hasErrors()) {
                model.put("roles", roleService.getAllRoles());

                return "addUser";
            }

            userService.modifyUser(user);

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/addRider")
    public String addRider(ModelMap model, ServletRequest servletRequest) {
        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            model.put("rider", new Rider());
            model.put("users", riderService.getRiderCandidates());
            model.put("locations", locationService.getAllLocations());

            return "addRider";
        }

        model.put("user", new User());

        return "login";
    }

    @PostMapping("/addRider")
    public String addRider(@Valid @ModelAttribute("rider") Rider rider,
                           BindingResult bindingResult,
                           ServletRequest servletRequest,
                           ModelMap model) {

        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            if (bindingResult.hasErrors()) {
                model.put("users", riderService.getRiderCandidates());
                model.put("locations", locationService.getAllLocations());

                return "addRider";
            }

            Location location = locationService.getLocationById(rider.getLocation().getId());

            if (isNull(location)) {
                bindingResult.rejectValue("location", "error.location", "Invalid location selected.");
                model.put("users", riderService.getRiderCandidates());
                model.put("locations", locationService.getAllLocations());

                return "addRider";
            }

            User user = userService.getUserById(rider.getUser().getId());

            if (isNull(user)) {
                bindingResult.rejectValue("user", "error.user", "Invalid user selected.");
                model.put("users", riderService.getRiderCandidates());
                model.put("locations", locationService.getAllLocations());
                return "addRider";
            }

            rider.setLocation(location);
            rider.setUser(user);
            rider.setStatus(RiderStatus.FREE);
            riderService.saveOrUpdateRider(rider);

            return "redirect:/admin";
        }

        model.put("user", new User());

        return "login";
    }

    @GetMapping("/rider/{id}/orders")
    public String riderOrders(@PathVariable("id") Integer id, ModelMap model, ServletRequest servletRequest) {
        if (authorization.checkAuthorization(servletRequest, ADMIN)) {
            List<Order> assignedOrders = riderService.getAssignedOrdersForRider(id);
            List<Order> completedOrders = riderService.getCompletedOrdersForRider(id);

            model.put("assignedOrders", assignedOrders);
            model.put("completedOrders", completedOrders);

            return "riderOrders";
        }

        model.put("user", new User());

        return "login";
    }
}