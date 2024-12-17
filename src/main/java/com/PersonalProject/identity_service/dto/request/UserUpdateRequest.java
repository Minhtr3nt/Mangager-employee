package com.PersonalProject.identity_service.dto.request;

import com.PersonalProject.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotNull(message = "FIRST_NAME_REQUIRED")
     String firstName;
    @NotNull(message = "LAST_NAME_REQUIRED")
    String lastName;

     String password;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;

}
