package com.model.guesthousebooking.Config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String role;

    public JwtAuthenticationToken(Object principal, String role, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.role = role;
        setAuthenticated(true);
        System.out.println("JwtAuthenticationToken created: Principal=" + principal + ", Role=" + role);
    }

    @Override
    public Object getCredentials() {
        return null; // JWT-based authentication does not require credentials
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationToken{" +
                "principal=" + principal +
                ", role='" + role + '\'' +
                ", authorities=" + getAuthorities() +
                '}';
    }
}