# server

## Requirements
- Git 
- JDK 8

## Config
Configuration settings are contained in *.properties* files within the [resources](src/main/resources) directory.
A specific properties file can be selected by setting the *SPRING_PROFILES_ACTIVE* environment variable.

```console

# linux, mac os x
$ export SPRING_PROFILES_ACTIVE=dev

# windows
$ set SPRING_PROFILES_ACTIVE=dev
```

### Using Environment Variables
The application server ships with an [application.properties](src/main/resources/application.properties) file which relies on the following environment variables.
All variables must be defined and valid for the server to function.

| Variable              | Function                                   | 
|-----------------------|--------------------------------------------|
| JLO_SERVER_PORT       | port that the server will listen on.       |
| JLO_SERVER_CORS       | if equal to 'YES' CORS is allowed          | 
| JLO_DB_URL            | url or ip of the database                  | 
| JLO_DB_PORT           | port of the database                       |
| JLO_DB_NAME           | name of the database                       |
| JLO_DB_USER           | database username                          |
| JLO_DB_PASS           | password corresponding with JLO_DB_USER    |
| JLO_STORAGE_DIRECTORY | directory where audio files will be stored |
| JLO_TS_API            | voice transcription api url                |
| JLO_JWT_TTL           | time to live for tokens                    |
| JLO_JWT_SECRET        | secret for signing tokens                  | 

### Using a development properties file
Alternatively an *application-dev.properties* file can be created within the [resources](src/main/resources) directory.
This file will be ignored by git so credentials can be placed within it directly. 

### Using the in-memory database
A profile ([application-h2.properties](src/main/resources/application-h2.properties)) to run the server with an H2 in-memory database is included. 


## Project Setup
```console
$ git clone https://github.com/malinoskj2/JOLO-Web-Test

$ cd Jolo-Web-Test/server

# linux, mac os x
$ ./mvnw install

# windows
$ mvnw.cmd install
```

## Run Development Server
```console
# linux, mac os x
$ ./mvnw spring-boot:run

# windows
$ mvnw.cmd spring-boot:run
```

## Build
```console
# linux, mac os x
$ ./mvnw compile

# windows
$ mvnw.cmd compile
```
