package com.example.security.manager;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class TokenManager {

    private final static Set<String> tokens = new ConcurrentSkipListSet<>();


    public void add(String token) {
        tokens.add(token);
    }

    public boolean isTokenExist(String token) {
        return tokens.contains(token);
    }
}
