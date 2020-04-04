package com.kiwiko.users.web;

import com.kiwiko.mvc.json.data.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.mvc.requests.api.RequestBodyParameter;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserAPIController {

    @Inject
    private UserService userService;

    @GetMapping(path = "/users/api/get/byEmail/{emailAddress}")
    public ResponseEntity<ResponsePayload> getUserDataByEmailAddress(@PathVariable(name = "emailAddress") String emailAddress) {
        User user = userService.getByEmailAddress(emailAddress)
                .orElse(null);

        return new ResponseBuilder()
                .withBody(user)
                .toResponseEntity();
    }

    @PostMapping(path = "/users/api/create")
    public ResponseEntity<ResponsePayload> createUser(@RequestBody User user) {
        User result = userService.create(user);
        return new ResponseBuilder()
                .withBody(result)
                .toResponseEntity();
    }

    @PostMapping(path = "/users/api/credentials/authenticate")
    public ResponseEntity<ResponsePayload> authenticateUserCredentials(
            @RequestBodyParameter(name = "emailAddress") String emailAddress,
            @RequestBodyParameter(name = "password") String password) {
        boolean isValid = userService.isValidUser(emailAddress, password);
        return new ResponseBuilder()
                .withBody(isValid)
                .toResponseEntity();
    }
}
