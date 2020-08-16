# Labforward Code Challenge for Shaaban Ebrahim

we gonna discuss how you can run the application and what are the features

## Introduction

You can run the application by typing

	./gradlew bootRun

This will start up a Spring Boot application with Tomcat server running on 8080.

Show all other possible tasks:

	./gradlew tasks
	
OR if you want to create aa docker image and run it in 8080 port  

	 sh ./scripts/run-project.sh
	
							
## Features

        1- showing all greeting messages
        2- updating features for each message
        3- deleting feature for each message
        4- create a new one 
        5- the application supports simple Thymleaf UI and Rest API integration 
        6- support create docker images to run it locally or upload it to dockerhub
        7- sonarqube reviewed the code (no code smells or Vulnerabilities) 
        8- show all Greetings, update, delete and creating greeting from you
                using http://localhost:8080
   


