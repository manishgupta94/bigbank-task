# test-assignment
**Description**
This repository contains solution for the test task as given in https://dragonsofmugloar.com/.  

This is a Scripting adventure (Backend) solution. 

---

## Technology Stack Used
1. JAVA 8
2. Spring Boot
4. Maven (pom.xml) is present for dependency management.

---

## Artifacts
Please go through this section to find more about the key artifacts used in the solution

1. All the model used in the application are inside **src/main/java/com/bigbank/dragonsOfMugloar/application/model**
2. The Exception files are placed inside **src/main/java/com/bigbank/dragonsOfMugloar/application/exception**  
3. The service files that calls REST APIs is at **src/main/java/com/bigbank/dragonsOfMugloar/application/service/GameService.java**  
4. The util files which has algorithm for selecting task and buying items is at **src/main/java/com/bigbank/dragonsOfMugloar/application/util/GameHelper.java**  
5. The GameEngine.java which runs the game is at **src/main/java/com/bigbank/dragonsOfMugloar/application/GameEngine.java**  
5. The GameTest.java which test the game for multiple iterations and checks if the scores are over 1000 or not is at **src/test/java/com/bigbank/dragonsOfMugloar/GameTest.java**  
6. The Spring Boot application with main method is at **src/main/java/com/bigbank/dragonsOfMugloar/DragonsOfMugloarApplication.java** which also implements run method of CommandLineRunner interface to kick start the game.  
7. For Test, a different Spring Boot application is used located at **src/main/java/com/bigbank/dragonsOfMugloar/DragonsOfMugloarApplication.java**  

---

## Steps to play the game
STEP 1: Clone the Spring Boot project from Github on local machine.  
STEP 2: Import the solution into IntelliJ as maven project.  
STEP 3: Select **DragonsOfMugloarAppliacation**, right click and select **Run DragonsOfMugloarAppliacation**.  
STEP 4: In the console, press Enter to start the game.  
STEP 5: The console will print your final score in the console.  
STEP 6: Repeat step 4-5 to keep playing the game.  
STEP 7: Press **N** to stop playing the game.  



---

# Pre-requisites
1. Java 8 should be installed.

---

## Test
1. You can run the file **src/test/java/com/bigbank/dragonsOfMugloar/GameTest.java**.  
2. You can change the number of runs for the game testing by modifying variable **numberOfRuns** inside test method.  

---

## Notes
Please keep in mind the following:

1. This is a console based application.  
2. User need to provide customer input to continue playing the game.  
3. Output scores are printed on the console.  
4. DragonsOfMugloarApplication.java runs spring boot application and also runs CommandLineRunner interface's run method which then starts the game.  

---