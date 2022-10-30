package com.example.security.security.providers;

import com.example.security.manager.TokenManager;
import com.example.security.security.model.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenManager tokenManager;

    public TokenAuthenticationProvider(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = authentication.getPrincipal().toString();

        if (tokenManager.isTokenExist(token)) {
            return new TokenAuthentication(token, null, null);
        }

        throw new BadCredentialsException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
