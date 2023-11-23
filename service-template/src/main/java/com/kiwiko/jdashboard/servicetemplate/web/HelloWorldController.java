package com.kiwiko.jdashboard.servicetemplate.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/public-api/test")
    public String testGet() {
        return "Hello, world!";
    }
}
