package com.example.security.security.providers;

import com.example.security.database.entities.UserOtp;
import com.example.security.database.repository.JpaUserOtpRepository;
import com.example.security.security.model.OTPAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OneTimePasswordAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserOtpRepository otpRepository;

    public OneTimePasswordAuthenticationProvider(JpaUserOtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();

        Optional<UserOtp> userOtpByUserName = otpRepository.findUserOtpByUserName(userName);

        if (userOtpByUserName.isPresent()) {
            return new OTPAuthentication(userName, userOtpByUserName.get().getOtp());
        }

        throw new BadCredentialsException("Invalid key pair");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthentication.class.equals(authentication);
    }
}
