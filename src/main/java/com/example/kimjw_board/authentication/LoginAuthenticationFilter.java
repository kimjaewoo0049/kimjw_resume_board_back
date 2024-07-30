package com.example.kimjw_board.authentication;

import com.example.kimjw_board.utils.JwtUtil;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;



public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    public LoginAuthenticationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager){
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override  // Spring SecruityConfig Start!!!
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(obtainUsername(request));
        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(a);
    }

    @Value("${jwt.signing.key}")
    private String testkey;

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        int principal = (int)authResult.getPrincipal();
        String userId = obtainUsername(request);

        String token = jwtUtil.createToken(principal, userId, role, 60*60*1000L);
        System.out.println("로그인후 최초 발급된 토큰" + token);
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
