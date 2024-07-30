package com.example.kimjw_board.configs;

import com.example.kimjw_board.authentication.JwtAuthenticationFilter;
import com.example.kimjw_board.authentication.LoginAuthenticationFilter;
import com.example.kimjw_board.authentication.UserNamePasswordAuthenticationProvider;
import com.example.kimjw_board.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecruityConfig {

    private final JwtUtil jwtUtil;
    private final UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider;

    public SecruityConfig(JwtUtil jwtUtil, UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider) {
        this.jwtUtil = jwtUtil;
        this.userNamePasswordAuthenticationProvider = userNamePasswordAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http)throws Exception{
        http.formLogin((formLogin) -> formLogin.disable());
        http.httpBasic((httpBasic) -> httpBasic.disable());
        http.csrf((csrf) -> csrf.disable());
        http.addFilterAt(new LoginAuthenticationFilter(jwtUtil, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), LoginAuthenticationFilter.class);

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/user/signup").permitAll()  // 이 url은 인증없이 가능
                .requestMatchers(HttpMethod.GET,"/test").permitAll() // /test url로 요청이 들어오면 get요청은 무조건 허용 post는 인증진행
                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/join").permitAll()
                .requestMatchers(HttpMethod.POST,"/userlist").permitAll()
                .requestMatchers(HttpMethod.POST,"/post-count").permitAll()
                .requestMatchers(HttpMethod.POST,"/board-list").permitAll()
                .requestMatchers(HttpMethod.POST,"/content-view").permitAll()
                .requestMatchers(HttpMethod.POST,"/board").permitAll()
                .requestMatchers(HttpMethod.POST,"/user-check").hasAnyAuthority("USER") // hasAnyAuthority은 접속권한을 지정한다.
                .requestMatchers(HttpMethod.POST,"/content-update").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST,"/content-create").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST,"/content-delete").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST,"/content-find").hasAnyAuthority("USER")
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager () throws Exception{
        return new ProviderManager(userNamePasswordAuthenticationProvider);
    }
}
