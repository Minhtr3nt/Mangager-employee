package com.PersonalProject.identity_service.repository;

import com.PersonalProject.identity_service.enity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
