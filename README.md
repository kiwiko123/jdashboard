# jdashboard
This is a web application made for my own personal learning, experimentation, and fun.

## Development information
This project was developed on a machine running macOS Big Sur.
Unless otherwise stated, any setup instructions may be specific to this environment.

The following development environment was used:
* macOS 15.2 
* IntelliJ IDEA Community Edition 2024.3.1.1
* Java 17 (OpenJDK)
* React 16.8.6
* PostgreSQL 17.2

## Installation setup
Install Homebrew: https://brew.sh/.

Run [`setup.sh`](./setup.sh) to install Java, node, Maven, and PostgreSQL:
```shell
sh ./setup.sh
```

### PostgreSQL
Control the PostgreSQL server manually with the following commands:
```shell
/opt/homebrew/opt/postgresql@17/bin/pg_ctl start -D /opt/homebrew/var/postgresql@17
/opt/homebrew/opt/postgresql@17/bin/pg_ctl stop -D /opt/homebrew/var/postgresql@17
```

#### One-time setup
Start the local PostgreSQL server, then create a new user and database:
```shell
/opt/homebrew/opt/postgresql@17/bin/pg_ctl -D /usr/local/var/postgres start
/opt/homebrew/opt/postgresql@17/bin/createuser jdashboard_tester
/opt/homebrew/opt/postgresql@17/bin/createdb jdashboard_test
/opt/homebrew/opt/postgresql@17/bin/createdb jdashboard_framework_internal_test
```

#### Start-up
Enter your database's shell with `psql`:
```shell
psql -U jdashboard_tester jdashboard_test -h localhost -p 5432
```
Re-create the database artifacts by copying the contents of [`schema.sql`](./src/main/resources/sql/schema.sql) and 
[`indexes.sql`](./src/main/resources/sql/indexes.sql) into the Postgres shell and running them, in that order. 
Exit the shell with `\q`.

### React
#### NPM dependencies
Run the script [`setup.sh`](./react/docs/setup.sh) to install all required NPM dependencies:
```shell
sh ./react/docs/setup.sh
```

## Start the application
1. To start the web application, run `./gradle run` from the project root.
2. To start the client application:
```shell
cd ./react
npm start
```
3. Navigate to http://localhost:3000/home.

### Open JDK Side effects
#### Strong encapsulation
With OpenJDK 16+, some Java packages containing non-critical APIs will be closed by default. 
This prevents non-public data from being accessible by reflection. 
Jdashboard uses reflection for many things, including object serialization and data change capture. 

You may need to add the following as a JVM option to permit open visibility to the `java.time` packages.
```
--add-opens java.base/java.time=ALL-UNNAMED
```

https://openjdk.java.net/jeps/396