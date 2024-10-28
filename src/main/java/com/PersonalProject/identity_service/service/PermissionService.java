package com.PersonalProject.identity_service.service;


import com.PersonalProject.identity_service.dto.request.PermissionRequest;
import com.PersonalProject.identity_service.dto.response.PermissionResponse;
import com.PersonalProject.identity_service.enity.Permission;
import com.PersonalProject.identity_service.mapper.PermissionMapper;
import com.PersonalProject.identity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

     public PermissionResponse create(PermissionRequest request){
         Permission permission  =  permissionMapper.toPermission(request);
         permission =  permissionRepository.save(permission);
         return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
         var permissions = permissionRepository.findAll();
         return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
         permissionRepository.deleteById(permission);
    }
}