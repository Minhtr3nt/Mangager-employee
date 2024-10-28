package com.PersonalProject.identity_service.mapper;

import com.PersonalProject.identity_service.dto.request.PermissionRequest;
import com.PersonalProject.identity_service.dto.request.UserCreationRequest;
import com.PersonalProject.identity_service.dto.request.UserUpdateRequest;
import com.PersonalProject.identity_service.dto.response.PermissionResponse;
import com.PersonalProject.identity_service.dto.response.UserResponse;
import com.PersonalProject.identity_service.enity.Permission;
import com.PersonalProject.identity_service.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);


}
