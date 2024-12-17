package com.PersonalProject.identity_service.dto.request;

import com.PersonalProject.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotNull(message = "FIRST_NAME_REQUIRED")
    String firstName;
    @NotNull(message = "LAST_NAME_REQUIRED")
    String lastName;
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "USERNAME_INVALID_FORMAT")
    @Size(min = 3, message = "USERNAME_INVALID")
     String username;

    @Size(min = 8 , message = "PASSWORD_INVALID")
    String password;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;


}
