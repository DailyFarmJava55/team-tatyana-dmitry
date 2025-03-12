
package telran.auth.account.validation;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
    	 List<String> errorMessages = new ArrayList<>();

         if (password == null) {
             errorMessages.add("Password cannot be null");
         } else {
             if (password.length() < 8) {
                 errorMessages.add("Password must be at least 8 characters long");
             }
             if (!password.matches(".*\\d.*")) {
                 errorMessages.add("Password must contain at least one digit");
             }
             if (!password.matches(".*[A-Z].*")) {
                 errorMessages.add("Password must contain at least one uppercase letter");
             }
             if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                 errorMessages.add("Password must contain at least one special character");
             }
         }

         if (!errorMessages.isEmpty()) {
             context.disableDefaultConstraintViolation();
             for (String errorMessage : errorMessages) {
                 context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
             }
             return false;
         }

         return true;
    }
}
