package com.PersonalProject.identity_service.mapper;

import com.PersonalProject.identity_service.dto.request.RoleRequest;
import com.PersonalProject.identity_service.dto.request.UserCreationRequest;
import com.PersonalProject.identity_service.dto.request.UserUpdateRequest;
import com.PersonalProject.identity_service.dto.response.RoleResponse;
import com.PersonalProject.identity_service.dto.response.UserResponse;
import com.PersonalProject.identity_service.enity.Role;
import com.PersonalProject.identity_service.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
