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
public class emailValidator implements Validator {

    private final UserService userService;

    public emailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String email = user.getEmail();

        if (isNull(email) || email.isEmpty()) {
            errors.rejectValue("email", "email.empty",
                    "Required");

            return;
        }

        User user1 = userService.getUserByEmail(email);

        if (nonNull(user1) && !Objects.equals(user1.getId(), user.getId())) {
            errors.rejectValue("email", "email.invalid", "email must be unique");
        }
    }
}