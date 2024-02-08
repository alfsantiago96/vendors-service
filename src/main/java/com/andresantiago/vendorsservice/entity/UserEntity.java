package com.andresantiago.vendorsservice.entity;

import com.andresantiago.vendorsservice.enums.AuthenticationRoleEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String id;
    private String username;
    private String password;
    private List<AuthenticationRoleEnum> roles;
    private boolean isActive;
}