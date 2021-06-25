# Java
## Package structure
The current recommended pattern in the Jdashboard codebase structures Java code in the following packages:
* `api`
  * `interfaces`
  * `dto`
* `internal`
  * `data`
* `web`

This is not a hard and fast rule, but just a guideline. The recommended pattern may change in the future; 
in fact, many packages currently adhere to a deprecated structure that was recommended prior to this one.

Consider the following structure for some business logic about cars:
```
cars/
+--- api/
|------- interfaces/
|        +--------- CarService.java (interface)
|------- dto/
         +-- Car.java
|--- internal/
|    +------- data/
|    |        +--------- CarEntity.java
|    |        +--------- CarDataFetcher.java
|    +------- CarEntityMapper.java (converts JPA entities to DTOs)
|    +------- CarEntityService.java (implementation of CarService)
|--- web/
|    +-- CarAPIController.java
+--- CarConfiguration.java
```

### `api`
A public-facing package containing code intended for external consumption. 
The `api`-`internal` separation allows underlying implementations to be abstracted away. 
Often, this will consist of interfaces, DTOs, annotations, and exception classes. 
Any item within here is intended for use in other locations.

#### `api.interfaces`
A public-facing package that contains Java interfaces, annotations, and exception classes.

#### `api.dto`
A public-facing package that contains simplistic data classes.

### `internal`
A package that's meant to be internal to its directory.
That is, packages _outside_ of it should not directly access any of its classes.
An exception to this would be for dependency injection configuration beans.

This often consists of implementation classes for interfaces.

#### `internal.data`
For a web application, this also includes database-related objects like [JPA entities](https://en.wikipedia.org/wiki/Java_Persistence_API#Entities) and Data Access Objects (DAOs).
External consumers should access logic through public interfaces from `api`.

### `web`
A package that contains code related to web functionality, like [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) controllers.
