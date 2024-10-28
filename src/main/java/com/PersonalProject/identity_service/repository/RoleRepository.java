package com.PersonalProject.identity_service.repository;

import com.PersonalProject.identity_service.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {
}
