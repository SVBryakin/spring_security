package com.example.security.config;

import com.example.security.database.repository.JpaUserOtpRepository;
import com.example.security.manager.TokenManager;
import com.example.security.security.filters.TokeAuthenticationFilter;
import com.example.security.security.filters.UserNamePasswordAuthenticationFilter;
import com.example.security.security.providers.OneTimePasswordAuthenticationProvider;
import com.example.security.security.providers.TokenAuthenticationProvider;
import com.example.security.security.providers.UserNamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OneTimePasswordAuthenticationProvider oneTimePasswordAuthenticationProvider;
    @Autowired
    private UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider;
    @Autowired
    private JpaUserOtpRepository userOtpRepository;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @Autowired
    private TokenManager tokenManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(oneTimePasswordAuthenticationProvider)
                .authenticationProvider(userNamePasswordAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(userNamePasswordAuthenticationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(tokeAuthenticationFilter(), UserNamePasswordAuthenticationFilter.class);
    }

    @Bean
    public UserNamePasswordAuthenticationFilter userNamePasswordAuthenticationFilter() throws Exception {
        return new UserNamePasswordAuthenticationFilter(authenticationManagerBean(), userOtpRepository, tokenManager);
    }

    @Bean
    public TokeAuthenticationFilter tokeAuthenticationFilter() throws Exception {
        return new TokeAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
