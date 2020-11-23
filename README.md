# jdashboard
## Development Information
This project was developed on a machine running macOS Catalina.
Unless otherwise stated, any setup instructions may be specific to this environment.

The following development software was used:
* IntelliJ IDEA Community Edition 2019.2.4
* Java 9
* React 16.8.6
* PostgreSQL 12.2

## Java
The backend Java code is found under `src/com/kiwiko`.

### Package Structure
In general, the Java packages in this project adhere to the following structure:
* `api`
* `data`
* `internal`
* `web`

#### `api`
A public-facing package containing code intended for external consumption.
Often, this will consist of interfaces, annotations, and exception classes.

#### `data`
A public-facing package that contains simplistic data classes.
This mostly consists of [Data Transfer Objects](https://en.wikipedia.org/wiki/Data_transfer_object) (DTOs) and enums.

#### `internal`
A package that's meant to be internal to its directory.
That is, packages _outside_ of it should not directly access any of its classes.
An exception to this would be for dependency injection configurations.

This often consists of implementation classes for interfaces.

##### `internal.dataAccess`
For a web application, this also includes database-related objects like [JPA entities](https://en.wikipedia.org/wiki/Java_Persistence_API#Entities) and Data Access Objects (DAOs).
External consumers should access logic through public services and DTOs.

#### `web`
A package that contains code related to web functionality, like [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) controllers.
A `web` package may also wish to have its own `data` package alongisde it, particularly if it ingests web request payloads using Spring's `@RequestBody`.

#### Example Package Structure
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
```

## PostgreSQL
### Installation
Install PostgreSQL through Homebrew.
```shell script
$ brew install postgresql
```
Control the PostgreSQL server manually with the following commands:
```shell script
$ pg_ctl -D /usr/local/var/postgres start
$ pg_ctl -D /usr/local/var/postgres stop
```
### Initial Setup
Create a new user and database:
```shell script
$ createuser jdashboard_tester
$ createdb jdashboard_test
```
Enter your database's shell with [`psql`](https://www.postgresql.org/docs/12/app-psql.html):
```shell script
$  
```