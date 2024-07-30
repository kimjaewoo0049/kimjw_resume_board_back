package com.example.kimjw_board.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    public UserNamePasswordAuthenticationProvider (PasswordEncoder passwordEncoder, CustomUserDetailService customUserDetailService){
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = String.valueOf(authentication.getPrincipal());
        String userPw = String.valueOf(authentication.getCredentials());
        CustomUserDetail customUserDetail = customUserDetailService.loadUserByUsername(userId);

        if(passwordEncoder.matches(userPw, customUserDetail.getPassword())){
            Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetail.getUid(), null, customUserDetail.getAuthorities());
            return auth;
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}