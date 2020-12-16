# jdashboard
This is a web application made for my own personal learning, experimentation, and fun.

## Development information
This project was developed on a machine running macOS Big Sur.
Unless otherwise stated, any setup instructions may be specific to this environment.

The following development software was used:
* IntelliJ IDEA Community Edition 2020.3
* Java 15.0.1
* React 16.8.6
* PostgreSQL 13.1

## Installation setup
Install Homebrew: https://brew.sh/.

### Java
Download the Java SDK from Oracle: https://www.oracle.com/java/technologies/javase-downloads.html.

### PostgreSQL
Install PostgreSQL through Homebrew.
```shell
$ brew install postgresql
```
Control the PostgreSQL server manually with the following commands:
```shell
$ pg_ctl -D /usr/local/var/postgres start
$ pg_ctl -D /usr/local/var/postgres stop
```
#### One-time setup
Start the local PostgreSQL server, then create a new user and database:
```shell
$ pg_ctl -D /usr/local/var/postgres start
$ createuser jdashboard_tester
$ createdb jdashboard_test
```

#### Start-up
Enter your database's shell with [`psql`](https://www.postgresql.org/docs/12/app-psql.html):
```shell
$ psql -U jdashboard_tester jdashboard_test -h localhost -p 5432
```
Re-create the database artifacts by copying the contents of [`schema.sql`](./src/main/resources/sql/schema.sql) and 
[`indexes.sql`](./src/main/resources/sql/indexes.sql) into the Postgres shell and running them, in that order. 
Exit the shell with `\q`.

### React
Install nodejs, which includes React, through Homebrew.
```shell
$ brew install node
```

#### NPM dependencies
Run the script [`setup.sh`](./react/docs/setup.sh) to install all required NPM dependencies:
```shell
$ sh ./react/docs/setup.sh
```

## Start the application
1. Run [`Application.java`](./src/main/java/com/kiwiko/webapp/Application.java).
2. Run the app in development mode:
```shell
$ cd ./react
$ npm start
```
3. Navigate to http://localhost:3000/home.