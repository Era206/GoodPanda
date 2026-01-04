package goodpanda.propertyEditor;

import goodpanda.domain.User;
import goodpanda.service.UserService;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.isNull;

/**
 * @author sanjidaera
 * @since 22/12/24
 */
public class UserEditor extends PropertyEditorSupport {

    private final UserService userService;

    public UserEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) {
        if (isNull(text) || text.isEmpty()) {
            setValue(null);
        } else {
            try {
                int userId = Integer.parseInt(text);
                User user = userService.getUserById(userId);

                setValue(user);
            } catch (NumberFormatException e) {
                setValue(null);
            }
        }
    }
}