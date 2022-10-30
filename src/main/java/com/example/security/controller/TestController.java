package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "HELLO, TEST";
    }


    @GetMapping("/context/test")
    public String contextTest() {
        return "Authenticated";
    }
}
