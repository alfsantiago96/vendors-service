package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.UserEntity;
import com.andresantiago.vendorsservice.enums.AuthenticationRoleEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserDatabaseInMemory {

    List<UserEntity> users = new ArrayList<>();

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
                .roles(List.of(AuthenticationRoleEnum.USER,
                        AuthenticationRoleEnum.VENDOR_CREATOR,
                        AuthenticationRoleEnum.JOB_CREATOR))
                .build();

        UserEntity user = UserEntity.builder()
                .username("andre")
                .password("123")
                .roles(List.of(AuthenticationRoleEnum.USER))
                .build();

        UserEntity vendorCreator = UserEntity.builder()
                .username("vendor")
                .password("123")
                .roles(List.of(AuthenticationRoleEnum.VENDOR_CREATOR))
                .build();

        UserEntity jobCreator = UserEntity.builder()
                .username("job")
                .password("123")
                .roles(List.of(AuthenticationRoleEnum.JOB_CREATOR))
                .build();

        users.add(user);
        users.add(admin);
        users.add(vendorCreator);
        users.add(jobCreator);
    }
}