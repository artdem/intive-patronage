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

## Using the application

Application allows performing CRUD operations in Reservation System using CURL instructions.

```
F.ex. to add an example Conference room to the System: 
with your Reservation System application running, in your command line (f.ex. Git Bash shell for Windows) insert:
1. Adding Organization: 
curl -d '{"organizationName":"Organization name"}' -H "Content-Type: application/json" -X POST localhost:8080/organization

2. Adding Conference Room: 
curl -d '{"conferenceRoomName": "Conference Room name","floor": 10,"booked": true,"seats": 10}' -H "Content-Type: application/json" -X POST localhost:8080/organization/<Organization Id>/conferenceRoom
```
