package com.github.robsonrjunior.master.library.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class HalfStepValidator implements ConstraintValidator<HalfStep, BigDecimal> {

    private static final BigDecimal TWO = new BigDecimal("2");

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.multiply(TWO).stripTrailingZeros().scale() <= 0;
    }
}
