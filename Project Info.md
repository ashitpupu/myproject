# myproject

This repository contains my project on weather simulation. On start up of the project the system tries to analyze the data provided to it in trainingdata.csv and finds the min and max of various topogrphic values such as temp, pressure and humidity. Based on the min and max values of every month it predicts the topogrphic details of the same area. 

**Assumptions** :-

The system on initial start up it tries to learn from the csv file and stores the min max of temp, pressure and humidity. Based on these min and max it generates few new details. The details are generated based on the data provided it is not accurate. The application is confogured in such a way that user can provide month and year if the user wants, in case the user doesn't want to provide any details the system is enabled to take the current system date and base its calculation on that values. The list of location is obtained from the csv file only and the iteration can also be configured in the application properties file.

**Pre-requisite** :-

1- You need to have jdk installed min is jdk1.7

2- mvn should be installed and path configured to build the file.

**Usage** :- 

1- Once you have cloned/download the project into a folder.

2- Run the runScript.bat file by double clicking. The script will do following :-

  * run mvn clean
  * run mvn package.
  * Create a folder named Project_setup and will copy the 				weather_simulator-0.0.1-SNAPSHOT-jar-with-dependencies.jar jar file formed in target folder to Project_setup folder.
  * Copy the config folder into the Project_setup folder.
  *   The user if wants to change the month, year and no of iteration, the user can configure it in application property file which is placed in config folder.
  *    Once initial set up is completed, the project can run by clicking on the jar file or run the command 
  java -jar weather_simulator-0.0.1-SNAPSHOT-jar-with-dependencies.jar in command prompt.
3- The logs can be seen in the logs folder.