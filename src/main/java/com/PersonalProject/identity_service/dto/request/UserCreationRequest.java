package com.PersonalProject.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String firstName;
    String lastName;
    @Size(min = 3, message = "USERNAME_INVALID")
     String username;

    @Size(min = 8 , message = "PASSWORD_INVALID")
    String password;
    LocalDate dob;


}
