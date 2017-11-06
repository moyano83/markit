# Markit Test Application to retrieve financial information

 - Autor: Jorge Moyano
 - Date: 04/11/2017
 
## Prerequisites:

    - SBT 0.13.13
    - Scala 2.11.8 
 
## Usage:

You need to compile the project using the following command:

`sbt clean assembly`

Then, from the project folder, you can execute the application running the following command:

`scala -classpath target/scala-2.11/Markit.jar MarkitApp AMZN 2`
