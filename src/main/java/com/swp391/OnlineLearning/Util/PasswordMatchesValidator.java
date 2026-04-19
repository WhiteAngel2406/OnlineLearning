package com.swp391.OnlineLearning.Util;

import com.swp391.OnlineLearning.model.dto.UserDTO;
import jakarta.validation.ConstraintValidator;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object obj, jakarta.validation.ConstraintValidatorContext context) {
        UserDTO user = (UserDTO) obj;
        return user.getPassword().equals(user.getConfirmedPassword());
    }
}
