package goodpanda.controller;

import goodpanda.domain.Restaurant;
import goodpanda.domain.User;
import goodpanda.dto.SearchCriteria;
import goodpanda.service.*;
import goodpanda.service.*;
import goodpanda.validator.emailValidator;
import goodpanda.validator.nidValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;
import static goodpanda.domain.UserRole.ADMIN;
import static goodpanda.domain.UserRole.RIDER;

@Controller("/")
public class HomepageController {

    private static final Logger logger = LogManager.getLogger(HomepageController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private RoleService roleService;

    public static final String VIEW_HOMEPAGE = "homepage";

    @InitBinder("user")
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

        webDataBinder.addValidators(new nidValidator(userService));
        webDataBinder.addValidators(new emailValidator(userService));
    }

    @GetMapping
    public String homePage(ModelMap model,
                           @RequestParam(value = "locationId", required = false) Integer locationId) {

        String name = null;
        Integer categoryId = null;
        List<Restaurant> restaurants = restaurantService.searchRestaurants(locationId, categoryId, name);

        model.put("locations", locationService.getAllLocations());
        model.put("restaurants", restaurants);
        model.put("searchCriteria", new SearchCriteria(locationId, categoryId, name));

        return VIEW_HOMEPAGE;
    }

    @GetMapping("/signup")
    public String signup(ModelMap model) {
        model.put("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         @RequestParam("checkPassword") String password,
                         RedirectAttributes redirectAttributes,
                         HttpSession session,
                         ModelMap model) {

        if (bindingResult.hasErrors() || !password.equals(user.getPassword())) {
            if (!password.equals(user.getPassword())) {
                model.put("passwordError", "Passwords do not match!");
            }

            return "signup";
        }

        User savedUser = userService.modifyUser(user);
        redirectAttributes.addAttribute("id", savedUser.getId());
        session.setAttribute("user", savedUser);

        return "redirect:user/{id}";
    }

    @GetMapping("/login")
    public String login(ModelMap model, HttpSession session) {
        model.put("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        ModelMap model) {

        User user = userService.validateUser(email, password);

        if (nonNull(user)) {
            session.setAttribute("user", user);
            redirectAttributes.addAttribute("id", user.getId());

            if (user.getRoles().contains(roleService.getRoleByName(ADMIN))) {
                return "redirect:admin/";
            }

            if (user.getRoles().contains(roleService.getRoleByName(RIDER))) {
                session.setAttribute("rider", riderService.getRiderByUserId(user.getId()));
            }

            return "redirect:user/{id}";
        }

        model.put("error", "Invalid email or password");

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        if (nonNull(session)) {
            session.invalidate();
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        return "redirect:/";
    }
}
