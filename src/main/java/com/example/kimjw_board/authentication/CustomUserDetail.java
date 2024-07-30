package com.example.kimjw_board.authentication;

import com.example.kimjw_board.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetail implements UserDetails {

    private int uid;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetail(User user) {
        this.uid = user.getUid();
        this.username = user.getUserId();
        this.password = user.getUserPw();
        this.authorities = List.of(() -> userRatingMapping(user));
    }
    private String userRatingMapping (User user){
        String userRating = "";
        if(user.getUserRating().equals("0")){
            userRating = "ADMIN";
        } else if (user.getUserRating().equals("1")){
            userRating = "USER";
        } else{
            userRating = "DELETED";
        }
        return userRating;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
