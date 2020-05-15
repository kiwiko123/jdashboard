# jdashboard
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
$ psql -U jdashboard_tester jdashboard_test -h localhost -p 5432
```