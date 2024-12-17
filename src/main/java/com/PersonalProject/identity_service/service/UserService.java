package com.PersonalProject.identity_service.service;

import com.PersonalProject.identity_service.constant.PredefinedRole;
import com.PersonalProject.identity_service.dto.request.RoleRequest;
import com.PersonalProject.identity_service.dto.request.UserCreationRequest;
import com.PersonalProject.identity_service.dto.request.UserUpdateRequest;
import com.PersonalProject.identity_service.dto.response.UserResponse;
import com.PersonalProject.identity_service.enity.Role;
import com.PersonalProject.identity_service.enity.User;
import com.PersonalProject.identity_service.exception.AppException;
import com.PersonalProject.identity_service.exception.ErrorCode;
import com.PersonalProject.identity_service.mapper.RoleMapper;
import com.PersonalProject.identity_service.mapper.UserMapper;
import com.PersonalProject.identity_service.repository.RoleRepository;
import com.PersonalProject.identity_service.repository.UserRepository;
import com.PersonalProject.identity_service.specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){



        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);


        user.setRoles(roles);
        try {
            user = userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('CREATE_POST')")
    public List<UserResponse> getUsers(){

        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateUser(String Id, UserUpdateRequest request){
        User user = userRepository.findById(Id)
                .orElseThrow(()->new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        var User = userRepository.save(user);
        return userMapper.toUserResponse(User);
    }
    @PostAuthorize("returnObject.username == authentication.name") //Khi mà user name truyền vào trùng với username trả về thì cho phép chạy
    public UserResponse getUser(String Id){

            return userMapper.toUserResponse(userRepository.findById(Id)
                    .orElseThrow(()->new RuntimeException("User not found")));

    }
    public void deleteUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
         userRepository.deleteById(userId);

    }

    public List<UserResponse> searchUsers(String username, String firstName){
        Specification<User> specification = Specification.where(UserSpecification.hasUsername(username))
                .and(UserSpecification.hasFirstName(firstName));
        return userRepository.findAll(specification).stream().map(user -> {
            UserResponse userResponse = userMapper.toUserResponse(user);
            userResponse.setRoles(user.getRoles().stream().map(roleMapper::toRoleResponse).collect(Collectors.toSet()));
            return userResponse;
        }).toList();// trả về danh sách người dùng
    }



}
