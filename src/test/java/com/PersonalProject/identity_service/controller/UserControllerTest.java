package com.PersonalProject.identity_service.controller;

import com.PersonalProject.identity_service.constant.PredefinedRole;
import com.PersonalProject.identity_service.dto.request.ApiResponse;
import com.PersonalProject.identity_service.dto.request.UserCreationRequest;
import com.PersonalProject.identity_service.dto.request.UserUpdateRequest;
import com.PersonalProject.identity_service.dto.response.PermissionResponse;
import com.PersonalProject.identity_service.dto.response.RoleResponse;
import com.PersonalProject.identity_service.dto.response.UserResponse;
import com.PersonalProject.identity_service.enity.Permission;
import com.PersonalProject.identity_service.enity.Role;
import com.PersonalProject.identity_service.exception.ErrorCode;
import com.PersonalProject.identity_service.repository.RoleRepository;
import com.PersonalProject.identity_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request, requestUsernameInvalid;
    private UserUpdateRequest updateRequest;
    private UserResponse userResponse,userUpdateResponse;
    private LocalDate dob;


    @BeforeEach
    void initData(){

        dob = LocalDate.of(1990, 1, 1);

        // Dữ liệu cho test create user
        request =  UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("123458910")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("dqf3dw2er42e3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();


        // Dữ liệu truyền vào cho update User
        updateRequest = UserUpdateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .password("123458910")
                .dob(dob)
                .roles(List.of("USER"))
                .build();


        userUpdateResponse = UserResponse.builder()
                .id("dqf3dw2er42e3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .roles(Set.of(new RoleResponse("USER", "User role"
                        , Set.of(new PermissionResponse("APPROVE_POST", "Approve a post")))))
                .build();


        // Dữ liệu truyền vào cho create user với lỗi username truyền vào
        requestUsernameInvalid = UserCreationRequest.builder()
                .username("jo")
                .firstName("John")
                .lastName("Doe")
                .password("123458910")
                .dob(dob)
                .build();


    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN : là dữ liệu đầu vào chúng ta đã biết trước và dự đoán nó sẽ xảy ra như vậy
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                        .thenReturn((userResponse));

        //WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                                .value("dqf3dw2er42e3")
        );

    }
    @Test
    void createUser_UsernameInvalidRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule( new JavaTimeModule());
        String content = objectMapper.writeValueAsString(requestUsernameInvalid);



        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("User name must be at least 3 characters")

        );
    }
    @Test
    @WithMockUser
    void updateUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(updateRequest);
        Mockito.when(userService.updateUser(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(userUpdateResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/{userId}", "dqf3dw2er42e3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                        .value("dqf3dw2er42e3"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username")
                        .value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.dob")
                        .value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.roles[0].permissions[0].name")
                        .value("APPROVE_POST"))
        ;

    }
    @Test
    @WithMockUser
    void deletedUser() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(ArgumentMatchers.anyString());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/{UserId}", "dqf3dw2er42e3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("dqf3dw2er42e3"))
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("result").value("User has been deleted"));

    }
}
