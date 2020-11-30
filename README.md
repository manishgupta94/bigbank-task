# test-assignment
**Description**
This repository contains solution for the test task as given in [Dragons of Mugloar](http://www.dragonsofmugloar.com).  

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
STEP 4: In the console, press number of times you want to play the game and then enter.  
STEP 5: In the console, the game will output your scores for all the times and your success rate. 



---

# Pre-requisites
1. Java 8 should be installed.

---

## Test
1. You can run the file **src/test/java/com/bigbank/dragonsOfMugloar/GameTest.java**.  
2. You can change the number of runs for the game testing by modifying variable **numberOfRuns** inside test method.  

---

## Notes
Please note:

1. This is a console based application.  
2. User need to provide customer input to continue playing the game.  
3. Output scores are printed on the console.  
4. For each game, the console prints current list of Messages, which message is selected to be attempted, and Game state after attempt.  
5. There might be too much logs on the console while it runs, but it gives the summary at the end.  
6. The game sometimes fails to achieve over 1000. in average 2 out of 30 game plays.  
7. There is still room for improvement in the game algorithm.  
8. Sometimes, the message list returned by API contains corrupt message which id doesn't exist in the API backend. My code handles such Message by assigning the MINIMUM Integer score so that it does't get selected over others.  

---

## Game Algorithm

1. I assigned a base score corresponding to various probability in a task. Easier probability gets higher score and difficult task gets lower score.  
2. The base score is assigned based on the rate of success and failure over multiple API calls for each probability.  
3. The task is sorted based on the probability score, reward score and expiry score. The task with maximum score is selected to be solved.    
4. If the task attempt fails 3 times for a specific probability, the base score for that probability is reduced by a margin.  
5. Based on current life count and gold value, Healing potion is bought.  
6. Based on current gold value, dragon item is bought keeping gold reserved for atleast 2 lives.  


---

## Improvements

1. Find the relation between various Reputation (with people, state, and underworld) and task failure rate to help
selecting the best task.  


---