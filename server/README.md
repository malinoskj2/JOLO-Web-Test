# server

## Requirements
- Git 
- JDK 8

## Config
A valid config is required for the project to function. This can be defined in 2 ways.

### Using Environment Variables
The application server ships with an [application.properties](src/main/resources/application.properties) file which relies on the following environment variables.
All variables must be defined and valid for the server to function.

| Variable        | Function                                | 
|-----------------|-----------------------------------------|
| JLO_SERVER_PORT | port that the server will listen on.    |
| JLO_DB_URL      | url or ip of the database               | 
| JLO_DB_PORT     | port of the database                    |
| JLO_DB_NAME     | name of the database                    |
| JLO_DB_USER     | database username                       |
| JLO_DB_PASS     | password corresponding with JLO_DB_USER |

### Using a development properties file
Alternatively an *application-dev.properties* file can be created within the [resources](src/main/resources) directory.

This file will be ignored by git so credentials can be placed within it directly. 
Appending 
```console
-Dspring.profiles.active=dev
``` 
to mvnw commands will use the properties defined in 'application-dev.properties' 

### Project Setup
```console
$ git clone https://github.com/malinoskj2/JOLO-Web-Test

$ cd Jolo-Web-Test/server

# linux, mac os x
$ ./mvnw install

# windows
$ mvnw.cmd install
```

### Run Development Server
```console
# linux, mac os x
$ ./mvnw spring-boot:run

# windows
$ mvnw.cmd spring-boot:run
```

### Build
```console
# linux, mac os x
$ ./mvnw compile

# windows
$ mvnw.cmd compile
```
