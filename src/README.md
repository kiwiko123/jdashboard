# Java
## Package structure
In general, the Java packages in this project adhere to the following structure:
* `api`
* `data`
* `internal`
* `web`

Consider the following structure for some business logic about cars:
```
cars/
+--- api/
|    +-- CarService.java (interface)
|--- data/
|    +--- Car.java (DTO)
|--- internal/
|    +------- dataAccess/
|    |        +--------- CarDAO.java
|    |        +--------- CarEntity.java
|    +------- CarEntityMapper.java (converts JPA entities to DTOs)
|    +------- CarEntityService.java (implementation)
|--- web/
|    +-- CarAPIController.java
+--- CarConfiguration.java
```

### `api`
A public-facing package containing code intended for external consumption.
Often, this will consist of interfaces, annotations, and exception classes.

### `data`
A public-facing package that contains simplistic data classes.
This mostly consists of [Data Transfer Objects](https://en.wikipedia.org/wiki/Data_transfer_object) (DTOs) and enums.

### `internal`
A package that's meant to be internal to its directory.
That is, packages _outside_ of it should not directly access any of its classes.
An exception to this would be for dependency injection configuration beans.

This often consists of implementation classes for interfaces.

#### `internal.dataAccess`
For a web application, this also includes database-related objects like [JPA entities](https://en.wikipedia.org/wiki/Java_Persistence_API#Entities) and Data Access Objects (DAOs).
External consumers should access logic through public services from `api`.

### `web`
A package that contains code related to web functionality, like [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) controllers.
A `web` package may also wish to have its own `data` package alongside it, particularly if it ingests web request payloads using Spring's `@RequestBody`.

### `impl`
A package that contains publicly-consumable implementation classes.
This is most commonly used for abstract classes that house some business logic but can be extended for custom use cases.