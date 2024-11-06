package com.PersonalProject.identity_service.configuration;

import com.PersonalProject.identity_service.constant.PredefinedRole;
import com.PersonalProject.identity_service.enity.Role;
import com.PersonalProject.identity_service.enity.User;
import com.PersonalProject.identity_service.repository.RoleRepository;
import com.PersonalProject.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Slf4j
public class ApplicationInitConfig {
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository){


        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<Role>();
                roleRepository.findById(PredefinedRole.ADMIN_ROLE).ifPresent(roles::add);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has  been created with default password: " +
                        "admin, please change it");
            }
        };
    }


}
