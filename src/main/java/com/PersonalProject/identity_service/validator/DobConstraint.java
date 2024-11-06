package com.PersonalProject.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) // trường có thể áp dụng annotation này, ở đây là chỉ biến thôi
@Retention(RetentionPolicy.RUNTIME)  // Xác định xử lý lúc nào
@Constraint(validatedBy = {DobValidator.class}) // class chịu trách nhiệm xử lý cho annotation này
public @interface DobConstraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
