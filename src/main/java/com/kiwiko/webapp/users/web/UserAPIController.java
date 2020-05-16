package com.kiwiko.webapp.users.web;

import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
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
}
