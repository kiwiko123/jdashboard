package com.kiwiko.jdashboard.permissions.service.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;
import com.kiwiko.jdashboard.permissions.service.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.permissions.service.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
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
