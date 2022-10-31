package com.example.security.security.filters;

import com.example.security.database.entities.UserOtp;
import com.example.security.database.repository.JpaUserOtpRepository;
import com.example.security.manager.TokenManager;
import com.example.security.security.model.OTPAuthentication;
import com.example.security.security.model.UsernamePasswordAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class UserNamePasswordAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;
    private final JpaUserOtpRepository userOtpRepository;
    private final TokenManager tokenManager;

    public UserNamePasswordAuthenticationFilter(AuthenticationManager manager, JpaUserOtpRepository userOtpRepository,
                                                TokenManager tokenManager) {
        this.manager = manager;
        this.userOtpRepository = userOtpRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        CharSequence userName = request.getHeader("User-name");
        CharSequence password = request.getHeader("Password");

        String otp = request.getHeader("OTP");

        if (Objects.isNull(otp)) {
            manager.authenticate(new UsernamePasswordAuthentication(userName, password));
            // we generate an OTP
            String code = String.valueOf(new Random().nextInt(9999) + 1000);
            UserOtp otpEntity = new UserOtp();
            otpEntity.setUserName(userName.toString());
            otpEntity.setOtp(code);
            userOtpRepository.save(otpEntity);
        } else {
            manager.authenticate(new OTPAuthentication(userName, otp));
            String token = UUID.randomUUID().toString();
            response.setHeader("Auth", token);
            tokenManager.add(token);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
