# DK287_TECHBITERS
Mobile game for Alzheimer's Disease Detection 

* **Download the following file from the given link and place it in the models folder inside Interlocking_Diagram folder**
      https://drive.google.com/drive/folders/1B32lUFUfOuil3oeqvpdYzROL_ddYIbOe?usp=sharing
      
* Create an AWS EC2 instance for clok Drawing (Source: Clock Drawing Folder)
   Goto app/src/main/java/com/ad_sih/clockdrawing.java file 
   Within the function "screenshot" enter your API End point in the ***line 271*** provided where "YOUR_API_END_POINT" for String postUrl
   ![clock-readme](https://user-images.githubusercontent.com/47329924/89140580-ad0ad880-d55f-11ea-9944-bf02042e2f19.PNG)

   
* Create an AWS EC2 instance for Interlocking Diagram (Source: Interlocking_Diagram Folder)
  Goto app/src/main/java/com/ad_sih/diagram.java file
  Within the function "screenshot" enter your API End point in the ***line 273*** provided where "YOUR_API_END_POINT" for String postUrl
  ![interlocking-readme](https://user-images.githubusercontent.com/47329924/89140654-d75c9600-d55f-11ea-91db-f7fbd22af971.PNG)

  
Building
========
To build you will need:

 * A Java compiler compatible with Java 1.8
 * The Android SDK with platform 26 installed
 Building from command-line
--------------------------
> Note: at the time of this writing, the current version of Gradle ([4.5.1](https://gradle.org/releases/)) is not compatible with the current version of JDK ([9.0.4](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)). To have the build succeed, use JDK version [1.8.0_162](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
 * `gradle build` to build the APK
 * Optional: `gradle installDebug` to install the APK to a connected device
 Building with Android Studio
---------------------
You can also build with Android Studio by importing this project into it.

Building from Eclipse
---------------------
You can also build from Eclipse. Create a new Android Project, choosing "Create
project from exisiting source", then set the compiler compliance level to 1.6
in project settings.
