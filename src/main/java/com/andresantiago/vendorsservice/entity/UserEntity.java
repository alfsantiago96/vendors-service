package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.enums.AuthenticationRoleEnum;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserEntity {

    private String username;
    private String password;
    private List<AuthenticationRoleEnum> roles;
}