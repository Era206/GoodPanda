package goodpanda.controller;

import goodpanda.domain.*;
import goodpanda.service.*;
import goodpanda.domain.*;
import goodpanda.dto.SearchCriteria;
import goodpanda.propertyEditor.FoodEditor;
import goodpanda.propertyEditor.LocationEditor;
import goodpanda.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static goodpanda.domain.UserRole.RESTAURANT_ADMIN;
import static goodpanda.domain.UserRole.RIDER;

/**
 * @author sanjidaera
 * @since 4/12/24
 */
@Controller
@RequestMapping("/user/{id}")
@SessionAttributes({"orderItems", "orderItem"})
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private RoleService roleService;

    List<OrderItem> orderItems = new ArrayList<>();

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        webDataBinder.registerCustomEditor(Food.class, new FoodEditor(foodService));
        webDataBinder.registerCustomEditor(Location.class, new LocationEditor(locationService));
    }

    @GetMapping
    public String searchRestaurants(@RequestParam(required = false) Integer categoryId,
                                    @RequestParam(required = false) Integer locationId,
                                    @RequestParam(required = false) String restaurantName,
                                    @PathVariable("id") Integer id,
                                    ModelMap model) {

        model.put("categories", categoryService.getAllCategories());
        model.put("locations", locationService.getAllLocations());

        List<Restaurant> restaurants = restaurantService.searchRestaurants(categoryId, locationId, restaurantName);
        model.put("restaurants", restaurants);
        model.put("searchCriteria", new SearchCriteria(categoryId, locationId, restaurantName));

        List<Order> completedOrders = userService.getCompletedOrdersByUser(userService.getUserById(id));
        List<Order> runningOrders = userService.getRunningOrdersByUser(userService.getUserById(id));

        model.put("previousOrders", completedOrders);
        model.put("runningOrders", runningOrders);

        User user = userService.getUserById(id);
        List<String[]> rolesWithUrls = new ArrayList<>();
        Integer riderId = null;

        for (Rider rider : riderService.getAllRiders()) {
            if (user.getId().equals(rider.getUser().getId())) {
                riderId = rider.getId();
            }
        }

        if (nonNull(user)) {
            rolesWithUrls.add(new String[]{"User Dashboard", "/user/" + id});

            if (user.getRoles().contains(roleService.getRoleByName(RESTAURANT_ADMIN))) {
                rolesWithUrls.add(new String[]{"Restaurant Admin Dashboard", "/restaurantAdmin/" + id});
            }

            if (user.getRoles().contains(roleService.getRoleByName(RIDER))) {
                rolesWithUrls.add(new String[]{"Rider Dashboard", "/rider/" + riderId});
            }
        }

        model.put("rolesWithUrls", rolesWithUrls);

        return "userDashboard";
    }

    @GetMapping("/order/{restaurantId}")
    public String addOrder(@PathVariable("id") Integer id,
                           @PathVariable("restaurantId") Integer restaurantId,
                           ModelMap model) {

        List<Food> foods = restaurantService.getRestaurantById(restaurantId).getFoods();
        List<Location> locations = restaurantService.getRestaurantById(restaurantId).getLocations();
        model.put("foods", foods);
        model.put("locations", locations);
        model.put("orderItems", orderItems);

        OrderItem orderItem = new OrderItem();
        model.put("orderItem", orderItem);

        return "addOrder";
    }

    @GetMapping("/order/{restaurantId}/{foodId}/addOrderItem")
    public String addOrderItem(@PathVariable("restaurantId") Integer restaurantId,
                               @PathVariable("foodId") Integer foodId,
                               @PathVariable("id") Integer id,
                               @ModelAttribute("orderItems") List<OrderItem> orderItems,
                               @ModelAttribute("orderItem") OrderItem orderItem,
                               ModelMap model) {

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Food food = foodService.getFoodById(foodId);
        orderItem.setFood(food);

        for (OrderItem orderItem1 : orderItems) {
            if (orderItem1.getFood().getId() == orderItem.getFood().getId()) {
                orderItem.setQuantity(orderItem1.getQuantity());
            }
        }

        model.put("orderItem", orderItem);
        model.put("orderItems", orderItems);
        model.put("id", id);

        return "addOrderItem";
    }

    @PostMapping("/order/{restaurantId}/{foodId}/addOrderItem")
    public String addOrderItem(@ModelAttribute("orderItem") OrderItem orderItem,
                               @ModelAttribute("orderItems") List<OrderItem> orderItems,
                               ModelMap model) {

        if (isNull(orderItem.getQuantity()) || orderItem.getQuantity() == 0) {
            return "redirect:/user/{id}/order/{restaurantId}";
        }

        orderItem.setId(null);
        orderItem.setUnitPrice(orderItem.getFood().getPrice() * orderItem.getQuantity());
        OrderItem removingItem = null;

        for (OrderItem orderItem1 : orderItems) {
            if (orderItem1.getFood().getId() == orderItem.getFood().getId()) {
                removingItem = orderItem1;
            }
        }

        if (nonNull(removingItem)) {
            orderItems.remove(removingItem);
        }

        orderItems.add(orderItem);
        model.put("orderItems", orderItems);

        return "redirect:/user/{id}/order/{restaurantId}";
    }

    @PostMapping("/order/{restaurantId}")
    public String addOrder(@PathVariable("id") Integer id,
                           @PathVariable("restaurantId") Integer restaurantId,
                           @ModelAttribute("orderItems") List<OrderItem> orderItems,
                           @RequestParam Location location,
                           @RequestParam(required = false) String deliveryAddress,
                           SessionStatus sessionStatus,
                           ModelMap model) {

        if (orderItems.isEmpty()) {
            return "redirect:/user/{id}";
        }

        Order order = new Order();
        order.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        order.setUser(userService.getUserById(id));
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderPlacementTime(LocalDateTime.now());
        order.setOrderDate(LocalDate.now());
        order.setLocation(location);
        order.setDeliveryAddress(deliveryAddress);
        orderService.saveOrUpdateOrder(order);
        sessionStatus.setComplete();

        return "redirect:/user/{id}";
    }
}
