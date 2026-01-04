package goodpanda.validator;

import goodpanda.domain.User;
import goodpanda.service.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 12/12/24
 */
public class nidValidator implements Validator {

    private final UserService userService;

    public nidValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String nid = user.getNid();

        if (isNull(nid) || nid.isEmpty()) {
            errors.rejectValue("nid", "nid.empty",
                    "Required");

            return;
        }

        User user1 = userService.getUserByNid(nid);

        if (nonNull(user1) && !Objects.equals(user1.getId(), user.getId())) {
            errors.rejectValue("nid", "nid.invalid", "nid must be unique");
        }
    }
}