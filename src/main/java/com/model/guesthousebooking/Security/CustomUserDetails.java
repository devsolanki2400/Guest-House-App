package com.model.guesthousebooking.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.guesthousebooking.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @JsonIgnore // Prevent recursive serialization
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRoles() == null) {
            return Collections.emptyList(); // No roles assigned
        }

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Map roles to authorities
                .toList(); // Use Collectors.toList() if using Java < 16
    }

    @Override
    @JsonIgnore // Prevent sensitive data from being serialized
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}