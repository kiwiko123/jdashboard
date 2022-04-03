package com.kiwiko.jdashboard.webapp.apps.grocerylist.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.GroceryListService;
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
@RequestMapping("/grocery-lists/public-api")
@UserPermissionCheck(PermissionNames.ADMIN)
public class GroceryListApiController {

    @Inject private GroceryListService groceryListService;

    @GetMapping("/{id}")
    public GroceryList getById(@PathVariable("id") long id) {
        return groceryListService.get(id).orElse(null);
    }

    @PostMapping("")
    public GroceryList create(@RequestBody GroceryList groceryList) {
        return groceryListService.create(groceryList);
    }

    @PutMapping("/{id}")
    public GroceryList update(@PathVariable("id") long id, @RequestBody GroceryList groceryList) {
        groceryList.setId(id);
        return groceryListService.update(groceryList);
    }

    @PatchMapping("/{id}")
    public GroceryList merge(@PathVariable("id") long id, @RequestBody GroceryList groceryList) {
        groceryList.setId(id);
        return groceryListService.merge(groceryList);
    }

    @DeleteMapping("/{id}")
    public GroceryList delete(@PathVariable("id") long id) {
        return groceryListService.delete(id);
    }
}
