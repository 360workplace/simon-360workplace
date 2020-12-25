package com.workplace.simon.validators;

import com.workplace.simon.model.User;
import com.workplace.simon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    public static final int MIN_USERNAME_LENGTH = 6;
    public static final int MAX_USERNAME_LENGTH = 32;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 32;

    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        validateUsernameAndPassword(errors, user);
        validateEmail(errors, user);
    }

    private void validateUsernameAndPassword(Errors errors, User user) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < MIN_USERNAME_LENGTH || user.getUsername().length() > MAX_USERNAME_LENGTH) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (this.getUserService().findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH || user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

    private void validateEmail(Errors errors, User user) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (this.getUserService().findByUsername(user.getUsername()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
    }
}
