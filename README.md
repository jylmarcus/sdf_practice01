This file is to document my thought process while doing this practice

I dont really understand what this jar file thing is since they didn't teach it, gonna google
Apparently I need to add some things to my pom file configuration
Added it under maven jar plugin
Wrote some test code in Main.java to see if it can compile 
Run mvn package from root folder to compile to a jar
cd target then use java -jar sdf_practice01-1.0-SNAPSHOT.jar to run the file
Ok seems like it works

I don't think they taught us about --port in class either
Looks like its a system property?
Gonna try to print System.getProperty("--port") to see if I can get it
Looks like it returns null
Checking if args[0] and args[1] returns the port properly
It does, I'll just hack it using these for now
Initialize a default port variable and a default docroot variable
Looks like they want a static directory variable of the current path?
In order to assign --port and --docroot, use a indexed for loop and a switch case to obtain the arguments
Whats the colon in directories for?
Looks like they want us to have more than 1 directory, using the colon as a seperator, use split method
Add to a filler list argPaths then check against static directory and convert to path
What data structure to use to contain the paths? simple arraylist
Port and docroot reading implemented in main

Based on assignment brief, it looks like they want us to abstract server functions to HttpServer
Reuse code from workshops and put into HttpServer
Create HttpServer with port number and list of paths
Add a method to check the paths as per task 4

Task 5
Add a method to start the server from main
Add thrPool member to HttpServer
Accept client connection and pass connection to HttpClientConnection handler

Task 6
Add socket member and constructor in HttpClientConnection
Need a way to read the input from browser (reading request from browser to server in assignment file)
Looks like only need to read the first line, so maybe try a BufferedReader.readline()
split readline by " " then use the elements of string array to fulfill actions in task 6
Need to pass paths list into httpclientconnection, create a member to store it
Logic: if the resource exists in any of the filePaths, resource exists and I should return true
I also need to record which filePath returned true
Handle this by using two conditionals in a for loop
Pseudocode: loop through possible file paths 
if file path exists and is a png, write action 4 message and transfer file, then close all open streams/readers and socket
if file path exists and is not a png, write action 3 message and transfer file, then close all open streams/readers and socket
end loop
not a conditional: if loop ends and client is still running, socket did not close which means resource does not exist, none of the file paths correspond to the resource
write action 2 message and close all open streams/readers and socket

Task 7
Technically not covered in our assessment but I need to do it to test the code

Troubleshooting
When running server and localhost:port/index.html, browser does not load page -> googled online and found that need to use printstream to write the return message
When running server, browser does not load css stylesheet -> in browser inspect sources, style.css has been requested by browser, problem is somewhere in html file or file naming
detailed explanation of how i fixed above issue:
using file.toPath().endsWith("png") returned false
using file.toPath().toString().endsWith("png") returned true
in order to serve the file correctly, content type message in the PrintStream must match the file eg. text/html for html, text/css for css, image/png for png image
After matching it properly, the css file loaded

Additional
Currently using a permanent while loop to run the server
Use ctrl+c in terminal to end the server