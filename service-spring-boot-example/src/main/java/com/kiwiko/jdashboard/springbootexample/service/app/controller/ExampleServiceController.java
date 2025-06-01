package com.kiwiko.jdashboard.springbootexample.service.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api")
public class ExampleServiceController {
    @GetMapping("/test")
    public String test() {
        return "Hello, Boo!";
    }
}
