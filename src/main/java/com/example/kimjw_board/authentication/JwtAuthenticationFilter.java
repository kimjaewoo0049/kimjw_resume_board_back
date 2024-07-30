package com.example.kimjw_board.authentication;

import com.example.kimjw_board.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");  // getHeader 메서드로 request객체의 해드를 jwt에 저장

        Enumeration<String> paramKeys = request.getParameterNames();  // request 파라미터 보기
        while (paramKeys.hasMoreElements()) {
            String key = paramKeys.nextElement();
            logger.info(key+":"+request.getParameter(key) + " request 내용");
        }

        if(jwt == null || !jwt.startsWith("Bearer ")){ // 그 jwt가 null이거나 Bearer로 시작하면
            filterChain.doFilter(request, response);
            return;
        }

        if(jwtUtil.isExpired(jwt)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // role과 userUid 를 SecurityContextHolder에 넣어줌
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(jwtUtil.getRole(jwt));
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUtil.getUserUid(jwt), null, List.of(grantedAuthority));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("글작성시 인증 받는 토큰" + jwt);

        filterChain.doFilter(request, response);
    }
}
