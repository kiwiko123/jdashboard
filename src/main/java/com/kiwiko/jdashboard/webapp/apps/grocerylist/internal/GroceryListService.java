package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal;

import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.GroceryList;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data.GroceryListEntityMapper;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

public class GroceryListService {

    @Inject private GroceryListEntityDataAccessObject groceryListEntityDataAccessObject;
    @Inject private GroceryListEntityMapper groceryListEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    public Optional<GroceryList> get(long id) {
        return crudExecutor.get(id, groceryListEntityDataAccessObject, groceryListEntityMapper);
    }

    public GroceryList create(GroceryList groceryList) {
        groceryList.setCreatedDate(Instant.now());
        return crudExecutor.create(groceryList, groceryListEntityDataAccessObject, groceryListEntityMapper);
    }

    public GroceryList update(GroceryList groceryList) {
        return crudExecutor.update(groceryList, groceryListEntityDataAccessObject, groceryListEntityMapper);
    }

    public GroceryList merge(GroceryList groceryList) {
        return crudExecutor.merge(groceryList, groceryListEntityDataAccessObject, groceryListEntityMapper);
    }

    public GroceryList delete(long id) {
        return crudExecutor.delete(id, groceryListEntityDataAccessObject, groceryListEntityMapper);
    }
}
