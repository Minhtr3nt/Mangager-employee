package com.PersonalProject.identity_service.dto.response;

import com.PersonalProject.identity_service.enity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String firstName;
    String lastName;
    String username;
    String password;
    LocalDate dob;
    Set<Role> roles;
}
