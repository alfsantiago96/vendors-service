package com.andresantiago.vendorsservice.config;

import com.andresantiago.vendorsservice.entity.UserEntity;
import com.andresantiago.vendorsservice.repository.UserDatabaseInMemory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Base64;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private final UserDatabaseInMemory userDatabaseInMemory;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isBasicAuthentication(request)){
            String[] credentials = decodeBase64(getHeader(request).replace(BASIC, ""))
                    .split(":");

            String username = credentials[0];
            String password = credentials[1];

            UserEntity user = userDatabaseInMemory.findUserByUsername(username);

            if(user == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User does not exist!");
                return;
            }

            boolean valid = user.getPassword().equals(password);

            if(!valid){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Password not match");
                return;
            }
            setAuthentication(user);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(UserEntity user) {
        Authentication authentication = createAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthenticationToken(UserEntity user) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private String decodeBase64(String base64) {
        byte[] decodeBytes = Base64.getDecoder().decode(base64);
        return new String(decodeBytes);
    }

    private boolean isBasicAuthentication(HttpServletRequest request) {
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
}