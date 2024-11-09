package com.PersonalProject.identity_service.Service;

import com.PersonalProject.identity_service.constant.PredefinedRole;
import com.PersonalProject.identity_service.dto.request.UserCreationRequest;
import com.PersonalProject.identity_service.dto.response.PermissionResponse;
import com.PersonalProject.identity_service.dto.response.RoleResponse;
import com.PersonalProject.identity_service.dto.response.UserResponse;
import com.PersonalProject.identity_service.enity.Permission;
import com.PersonalProject.identity_service.enity.Role;
import com.PersonalProject.identity_service.enity.User;
import com.PersonalProject.identity_service.repository.RoleRepository;
import com.PersonalProject.identity_service.repository.UserRepository;
import com.PersonalProject.identity_service.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserCreationRequest request;

    private LocalDate dob;
    private User user;
    private Role role;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void initData(){
       dob = LocalDate.of(1990, 1, 1);
        request =  UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("123458910")
                .dob(dob)
                .build();


        role = Role.builder()
                .name("USER")
                .description("User role")
                .permissions(Set.of(new Permission("APPROVE_POST", "Approve a post")))
                .build();
        user = User.builder()
                .id("dqf3dw2er42e3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .roles(Set.of(role))
                .build();

    }

    @Test
    void createUser_validRequest_success(){
        //GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(roleRepository.findById(ArgumentMatchers.eq(PredefinedRole.USER_ROLE))).thenReturn(Optional.ofNullable(role));
        //WHEN
        var response = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("dqf3dw2er42e3");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
        Assertions.assertThat(response.getRoles()).isEqualTo(Set.of(new RoleResponse("USER", "User role"
                , Set.of(new PermissionResponse("APPROVE_POST", "Approve a post")))));
        Assertions.assertThat(response.getDob()).isEqualTo(dob);
        Assertions.assertThat(response.getFirstName()).isEqualTo("John");
        Assertions.assertThat(response.getLastName()).isEqualTo("Doe");
    }
}
