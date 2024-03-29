package com.kiwiko.jdashboard.services.permissions.web;

import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.services.permissions.api.dto.Permission;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserPermissionCheck;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/permissions/api")
@JdashboardConfigured
@UserAuthCheck
public class PermissionsAPIController {

    @Inject private PermissionService permissionService;

    @GetMapping("/{id}")
    public Permission getById(@PathVariable("id") long id) {
        return permissionService.get(id).orElse(null);
    }

    @UserPermissionCheck(PermissionNames.ADMIN)
    @PostMapping("")
    public Permission create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }

    @UserPermissionCheck(PermissionNames.ADMIN)
    @PatchMapping("/{id}")
    public Permission merge(
            @PathVariable("id") long id,
            @RequestBody Permission permission) {
        permission.setId(id);
        return permissionService.merge(permission);
    }

    @UserPermissionCheck(PermissionNames.ADMIN)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        permissionService.remove(id);
    }
}
