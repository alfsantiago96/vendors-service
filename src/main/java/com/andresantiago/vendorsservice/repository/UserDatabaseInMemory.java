package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.entity.UserEntity;
import com.andresantiago.vendorsservice.enums.AuthenticationRoleEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDatabaseInMemory {

    List<UserEntity> users = new ArrayList<>();

    public List<UserEntity> findAllUsers() {
        return users;
    }

    public UserEntity findUserByUsername(String username) {
        return users.stream()
                .filter(userEntity -> userEntity.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @PostConstruct
    public void constructUsers() {
        UserEntity admin = UserEntity.builder()
                .username("vs_tech_challenge")
                .password("SuperSecurePassword123@")
                .roles(List.of(AuthenticationRoleEnum.ADMIN, AuthenticationRoleEnum.USER))
                .build();

        UserEntity user = UserEntity.builder()
                .username("andre")
                .password("123")
                .roles(List.of(AuthenticationRoleEnum.USER))
                .build();

        users.add(user);
        users.add(admin);
    }

//    public List<UserEntity> findUsersByRole(AuthenticationRoleEnum role) {
//       return users.stream()
//                .map(userEntity -> userEntity.getRoles().stream().allMatch(authenticationRoleEnum -> authenticationRoleEnum.equals(role))))
//                .collect(Collectors.toList());
//    }
}