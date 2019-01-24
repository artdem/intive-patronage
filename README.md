# intivePatronage 2019 - Backend recruitment task

Simple REST API-CRUD for conference room booking system prepared for intivePatronage 2019 recruitment process. 

## Building project

### Tools
```
Gradle build tool
IDE, f.ex. IntelliJ IDEA
CURL
```

### Building the application

In order to build the application, create a project in IDE using Gradle and this repository source code.

```
Using IntelliJ IDEA:
1. Open: File -> New -> Project from Version Control -> Git.
2. Clone this repository URL.
3. Import Gradle Project.
4. Choose: Use default gradle wrapper and Use auto-import.
```

### Starting the application using jar executable file

```
Windows:
1. Open CMD
2. Enter the catalog in which you store the application file
3. Use the following command: java -jar intivePatronage-0.1.0.jar
4. Application starts and is ready to use

Required Java version - 11.0.1
```

## Using the application

Application allows performing CRUD operations in Reservation System using CURL instructions.

```
F.ex. to add an example Conference room to the System: 
with your Reservation System application running, in your command line (f.ex. Git Bash shell for Windows) insert:
1. Adding Organization: 
curl -d '{"organizationName":"Organization name"}' -H "Content-Type: application/json" -X POST localhost:8080/organizations

2. Adding Conference Room: 
curl -d '{"conferenceRoomName": "Conference Room name","floor": "Floor between 0 and 10","booked": true,"seats": "Number of seats available", "organizationId": "Owner Organization Id"}' -H "Content-Type: application/json" -X POST localhost:8080/conferencerooms

3. Adding Reservation:
curl -d '{"reservationName": "Reservation name","reservationStart":"YYYY-mm-dd'T'HH:mm","reservationEnd":"YYYY-mm-dd'T'HH:mm", "conferenceRoomId": "Booked Conference Room Id"}' -H "Content-Type: application/json" -X POST localhost:8080/reservations
```
