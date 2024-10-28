package com.PersonalProject.identity_service.repository;

import com.PersonalProject.identity_service.enity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
