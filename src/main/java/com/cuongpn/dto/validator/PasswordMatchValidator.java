package com.cuongpn.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return object.getClass().getField("newPassword").toString().equals(object.getClass().getField("confirmPassword").toString());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
