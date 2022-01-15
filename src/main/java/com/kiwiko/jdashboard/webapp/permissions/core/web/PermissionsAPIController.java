package com.kiwiko.jdashboard.webapp.permissions.core.web;

import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.permissions.core.api.dto.Permission;
import com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.webapp.permissions.framework.api.annotations.PermissionRequired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/permissions/api")
@JdashboardConfigured
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class PermissionsAPIController {

    @Inject private PermissionService permissionService;

    @GetMapping("/{id}")
    public Permission getById(@PathVariable("id") long id) {
        return permissionService.get(id).orElse(null);
    }

    @PermissionRequired(PermissionNames.ADMIN)
    @PostMapping("")
    public Permission create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }

    @PermissionRequired(PermissionNames.ADMIN)
    @PutMapping("/{id}")
    public Permission merge(
            @PathVariable("id") long id,
            @RequestBody Permission permission) {
        return permissionService.merge(permission);
    }

    @PermissionRequired(PermissionNames.ADMIN)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        permissionService.remove(id);
    }
}
