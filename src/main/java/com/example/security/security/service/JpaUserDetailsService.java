package com.example.security.security.service;

import com.example.security.database.entities.User;
import com.example.security.database.repository.JpaUserRepository;
import com.example.security.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final JpaUserRepository userRepository;

    public JpaUserDetailsService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with name %s not found", username)));
        return new SecurityUser(user);
    }
}
