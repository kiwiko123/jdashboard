package com.kiwiko.jdashboard.services.usercredentials.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.UserCredentialService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/user-credentials/api")
@UserPermissionCheck(PermissionNames.ADMIN)
public class UserCredentialAPIController {

    @Inject private UserCredentialService userCredentialService;

    @GetMapping("/{id}")
    public UserCredential getById(@PathVariable("id") long id) {
        return userCredentialService.get(id).orElse(null);
    }

    @PostMapping("")
    public UserCredential create(@RequestBody UserCredential userCredential) {
        return userCredentialService.create(userCredential);
    }

    @PutMapping("/{id}")
    public UserCredential update(
            @PathVariable("id") long id,
            @RequestBody UserCredential userCredential) {
        userCredential.setId(id);
        return userCredentialService.update(userCredential);
    }

    @PatchMapping("/{id}")
    public UserCredential merge(
            @PathVariable("id") long id,
            @RequestBody UserCredential userCredential) {
        userCredential.setId(id);
        return userCredentialService.merge(userCredential);
    }

    @DeleteMapping("/{id}")
    public UserCredential delete(@PathVariable("id") long id) {
        userCredentialService.delete(id);
        return userCredentialService.get(id).orElse(null);
    }
}
