package com.wlopezob.api_user_v1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.wlopezob.api_user_v1.config.ValidationConfig;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<PasswordAnnotation, String> {
    
    private final ValidationConfig validationConfig;
    private Pattern pattern;

    @Override
    public void initialize(PasswordAnnotation constraintAnnotation) {
        pattern = Pattern.compile(validationConfig.getPassword().getPattern());
    }

    /**
     * Validates if the given password meets the required pattern criteria.
     * 
     * @param value The password string to validate
     * @param context The constraint validator context
     * @return true if the password matches the pattern, false if the password is null or doesn't match
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return pattern.matcher(value).matches();
    }
}
