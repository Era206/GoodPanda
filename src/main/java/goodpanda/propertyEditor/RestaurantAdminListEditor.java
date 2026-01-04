package goodpanda.propertyEditor;

import goodpanda.domain.User;
import goodpanda.service.UserService;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 1/12/24
 */
public class RestaurantAdminListEditor extends PropertyEditorSupport {

    private final UserService userService;

    public RestaurantAdminListEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) {

        if (isNull(text) || text.isEmpty()) {
            setValue(null);
            return;
        }

        String[] adminIds = text.split(",");
        List<User> restaurantAdmins = new ArrayList<>();

        for (String courseIdStr : adminIds) {
            try {
                int adminId = Integer.parseInt(courseIdStr);
                User restaurantAdmin = userService.getUserById(adminId);

                if (nonNull(restaurantAdmin)) {
                    restaurantAdmins.add(restaurantAdmin);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid course ID: " + courseIdStr);
            }
        }

        setValue(restaurantAdmins);
    }
}
