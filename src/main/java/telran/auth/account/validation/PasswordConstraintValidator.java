
package telran.auth.account.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        // Password must be at least 8 characters long, contain at least one digit, one uppercase letter, and one special character
        return password.length() >= 8 &&
               password.matches(".*\\d.*") &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}
