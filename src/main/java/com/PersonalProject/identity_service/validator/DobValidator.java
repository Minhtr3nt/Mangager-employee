package com.PersonalProject.identity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
        // hàm xử lý cho annotation đã được khai báo là DobConstraint.
    private int min;

    @Override           // là hàm xử lý data đúng không
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(value))
            return true;

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min;
    }

    @Override   // Nhận các giá trị cấu hình của DobConstraint
    // với mục đích tăng độ linh hoạt
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }
}

// Thường thì 1 annotation sẽ chỉ xử lý 1 điều kiện
