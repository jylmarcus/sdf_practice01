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