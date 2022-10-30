package com.example.security.security.providers;

import com.example.security.security.model.UsernamePasswordAuthentication;
import com.example.security.security.service.JpaUserDetailsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserNamePasswordAuthenticationProvider(JpaUserDetailsService userDetailsService,
                                                  @Lazy PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            return new UsernamePasswordAuthentication(userName, userDetails.getPassword(), userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Invalid key pair");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
