package com.andresantiago.vendorsservice.config;

import com.andresantiago.vendorsservice.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(UserEntity user){
        this.username = user.getUsername();
        this.password = user.getPassword();

        this.authorities = user.getRoles().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_".concat(role.name()));
        }).collect(Collectors.toList());
    }

    public static UserPrincipal create(UserEntity user){
        return new UserPrincipal(user);
    }
}