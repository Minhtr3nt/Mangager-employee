package com.PersonalProject.identity_service.repository;

import com.PersonalProject.identity_service.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

        boolean existsByUsername(String username);
        Optional<User> findByUsername(String username);
}
