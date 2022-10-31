package com.example.security.security.filters;

import com.example.security.security.model.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokeAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;

    public TokeAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Auth");
        TokenAuthentication tokenAuthentication = new TokenAuthentication(auth, null, null);
        Authentication authenticate = manager.authenticate(tokenAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login");
    }
}
