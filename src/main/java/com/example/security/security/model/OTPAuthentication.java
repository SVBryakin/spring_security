package com.example.security.security.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OTPAuthentication extends UsernamePasswordAuthenticationToken {
    public OTPAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OTPAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
