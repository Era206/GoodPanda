package goodpanda.controller;

import goodpanda.domain.*;
import goodpanda.service.*;
import goodpanda.domain.*;
import goodpanda.helper.AdminHelper;
import goodpanda.propertyEditor.CategoryListEditor;
import goodpanda.propertyEditor.LocationListEditor;
import goodpanda.propertyEditor.RestaurantAdminListEditor;
import goodpanda.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 10/12/24
 */
@Controller
@RequestMapping("/restaurantAdmin/{id}")
@SessionAttributes({"restaurantId", "restaurant", "completedOrders", "runningOrders", "pendingOrders"})
public class RestaurantAdminController {

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

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

        webDataBinder.registerCustomEditor(List.class, "locations",
                new LocationListEditor(locationService));

        webDataBinder.registerCustomEditor(List.class, "categories",
                new CategoryListEditor(categoryService));

        webDataBinder.registerCustomEditor(List.class, "restaurantAdmins",
                new RestaurantAdminListEditor(userService));
    }

    @GetMapping
    public String restaurantAdminDashboard(ModelMap model, @PathVariable("id") Integer adminId) {
        Restaurant restaurant = userService.getUserById(adminId).getRestaurant();

        if (nonNull(restaurant)) {
            List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
            List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);
            List<Order> pendingOrders = restaurantService.getPendingOrdersByRestaurant(restaurant);

            model.put("restaurant", restaurant);
            model.put("restaurantId", restaurant.getId());
            model.put("completedOrders", completedOrders);
            model.put("runningOrders", runningOrders);
            model.put("pendingOrders", pendingOrders);

            model.put("locations", locationService.getAllLocations());
            model.put("selectedLocations", restaurant.getLocations()
                    .stream()
                    .map(Location::getId)
                    .collect(Collectors.toList()));
        } else {
            model.put("error", "No restaurant found for this admin.");
        }

        return "restaurantAdminDashboard";
    }

    @GetMapping("/modifyRestaurant/{restaurantId}")
    public String updateRestaurant(ModelMap model,
                                   @PathVariable("restaurantId") Integer restaurantId,
                                   @PathVariable("id") Integer id) {

        Restaurant restaurant = (Restaurant) model.get("restaurant");

        List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
        List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);
        List<Order> pendingOrders = restaurantService.getPendingOrdersByRestaurant(restaurant);

        model.put("restaurant", restaurant);
        model.put("restaurantId", restaurantId);
        model.put("completedOrders", completedOrders);
        model.put("runningOrders", runningOrders);
        model.put("pendingOrders", pendingOrders);
        model.put("locations", locationService.getAllLocations());
        model.put("selectedLocations", restaurantService.getRestaurantById(restaurantId)
                .getLocations().stream()
                .map(Location::getId)
                .collect(Collectors.toList()));
        model.put("restaurantId", restaurantId);
        model.put("id", id);

        return "restaurantAdminDashboard";
    }

    @GetMapping("/deleteRestaurant/{restaurantId}")
    public String deleteRestaurant(@PathVariable("restaurantId") int restaurantId,
                                   @PathVariable("id") Integer id,
                                   ModelMap model) {

        restaurantService.deleteRestaurantById(restaurantId);
        adminHelper.prepareAndSetupLists(model);

        return "redirect:/restaurantAdmin/{id}";
    }

    @GetMapping("modifyRestaurant/{restaurantId}/addFood")
    public String addFood(@PathVariable("id") Integer id,
                          @PathVariable("restaurantId") Integer restaurantId,
                          ModelMap model) {

        model.put("food", new Food());
        model.put("categories", categoryService.getAllCategories());
        model.put("restaurantId", restaurantId);
        model.put("id", id);

        return "restaurantAddFoodForm";
    }

    @PostMapping("modifyRestaurant/{restaurantId}/addFood")
    public String addFood(@Valid @ModelAttribute("food") Food food,
                          BindingResult bindingResult,
                          @PathVariable("restaurantId") Integer restaurantId,
                          ModelMap model) {

        if (bindingResult.hasErrors()) {
            model.put("categories", categoryService.getAllCategories());

            return "restaurantAddFoodForm";
        }

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        food.setId(null);
        food.setRestaurant(restaurant);
        foodService.saveOrUpdateFood(food);
        restaurantService.saveOrUpdateRestaurant(restaurant);

        return "redirect:/restaurantAdmin/{id}";
    }

    @GetMapping("/acceptOrder/{orderId}")
    public String acceptOrder(@PathVariable("orderId") Integer orderId,
                              @PathVariable("id") Integer id,
                              ModelMap model) {

        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            System.err.println("Order not found for ID: " + orderId);
            model.put("error", "Order not found.");
            return "redirect:/restaurantAdmin/" + id;
        }

        if (nonNull(order)) {
            Restaurant restaurant = order.getRestaurant();

            if (isNull(restaurant)) {
                return "redirect:/restaurantAdmin/{id}";
            }

            if (order.getStatus() == OrderStatus.PLACED) {
                order.setStatus(OrderStatus.ACCEPTED);
                orderService.saveOrUpdateOrder(order);

                List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
                List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);
                List<Order> pendingOrders = restaurantService.getPendingOrdersByRestaurant(restaurant);

                model.put("completedOrders", completedOrders);
                model.put("runningOrders", runningOrders);
                model.put("pendingOrders", pendingOrders);

                return "redirect:/restaurantAdmin/{id}";
            } else {
                return "redirect:/restaurantAdmin/{id}";
            }
        }

        return "redirect:/restaurantAdmin/{id}";
    }

    @GetMapping("/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Integer orderId,
                              @PathVariable("id") Integer id,
                              ModelMap model) {

        Order order = orderService.getOrderById(orderId);

        if (nonNull(order)) {
            Restaurant restaurant = order.getRestaurant();

            if (isNull(restaurant)) {
                return "redirect:/restaurantAdmin/{id}";
            }

            if (order.getStatus() != OrderStatus.DELIVERED) {
                order.setStatus(OrderStatus.CANCELLED);
                orderService.saveOrUpdateOrder(order);
                List<Order> completedOrders = restaurantService.getCompletedOrdersByRestaurant(restaurant);
                List<Order> runningOrders = restaurantService.getRunningOrdersByRestaurant(restaurant);

                model.put("completedOrders", completedOrders);
                model.put("runningOrders", runningOrders);

                return "redirect:/restaurantAdmin/{id}";
            }
        }

        return "redirect:/restaurantAdmin/{id}";
    }


    @PostMapping("/modifyRestaurant/{restaurantId}")
    public String updateRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                   BindingResult bindingResult,
                                   @PathVariable("restaurantId") Integer restaurantId,
                                   @RequestParam(value = "restaurantAdminIds", required = false) List<Integer> restaurantAdminIds,
                                   ModelMap model) {

        Restaurant restaurant1 = restaurantService.getRestaurantById(restaurantId);
        restaurant1.setName(restaurant.getName());
        restaurant1.setDescription(restaurant.getDescription());
        restaurant1.setLocations(restaurant.getLocations());

        if (bindingResult.hasErrors()) {
            logger.info("Validation Errors: {}", bindingResult.getAllErrors());
            model.put("locations", locationService.getAllLocations());
            return "restaurantAdminDashboard";
        }

        if (nonNull(restaurantAdminIds)) {
            List<User> admins = restaurantAdminIds.stream()
                    .map(userService::getUserById)
                    .collect(Collectors.toList());
            restaurant.setRestaurantAdmins(admins);
            restaurant1.setRestaurantAdmins(admins);
        } else {
            restaurant1.setRestaurantAdmins(new ArrayList<User>());
        }

        restaurantService.saveOrUpdateRestaurant(restaurant1);

        return "redirect:/restaurantAdmin/{id}";
    }
}
