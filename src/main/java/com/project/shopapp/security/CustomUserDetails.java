package com.project.shopapp.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.shopapp.model.entity.User;

public class CustomUserDetails implements UserDetails {
    private final transient User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = user.getRole().getName();
        return Collections.singleton((GrantedAuthority) () -> roles);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }

    public Long getId() {
        return user.getId();
    }
}
