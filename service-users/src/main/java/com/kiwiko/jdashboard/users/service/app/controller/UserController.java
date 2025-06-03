package com.kiwiko.jdashboard.users.service.app.controller;

import com.kiwiko.jdashboard.users.service.dto.User;
import com.kiwiko.jdashboard.users.service.logic.UserProvisioner;
import com.kiwiko.jdashboard.users.service.logic.crud.UserReader;
import com.kiwiko.jdashboard.users.service.logic.dto.CreateUserInput;
import com.kiwiko.jdashboard.users.service.logic.dto.CreateUserOutput;
import jakarta.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api")
public class UserController {
    @Inject private UserReader userReader;
    @Inject private UserProvisioner userProvisioner;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long userId) {
        User user = userReader.get(userId).orElse(null);
        return user == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(user);
    }

    @PostMapping("/users/account")
    public CreateUserOutput createUserAccount(@RequestBody CreateUserInput createUserInput) {
        return userProvisioner.createNewUser(createUserInput);
    }
}
