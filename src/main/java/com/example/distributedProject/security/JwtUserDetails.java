package com.example.distributedProject.security;

import com.example.distributedProject.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
public class JwtUserDetails implements UserDetails {
    public Integer uuid;
    public String username;
    public String user_password;
    private Collection<? extends GrantedAuthority> authorities;

    private JwtUserDetails(Integer uuid,String user_name, String user_password, Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.username = user_name;
        this.user_password = user_password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("user"));
        return new JwtUserDetails(
                user.getUuid(),
                user.getUsername(),
                user.getUser_password(),
                authorityList
        );
    }



    @Override
    public String getPassword() {
        return user_password;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
