# Required software and executing the file

### ***OpenJDK 17+***: 

All imports are done from the standard GUI library (other than com.github.javafaker.Faker which would be explained further). For running the project, use either [Eclipse IDE](https://www.eclipse.org/downloads/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

### ***Java Faker (1.0.2)***: 

Java Faker has been used in this project to add random data into our student and bookings database for testing purposes. For this, we would need to first convert our project into a Maven project.

On Eclipse, we can do this by installing M2Eclipse from https://www.eclipse.org/m2e/ and then right-clicking on the project and selecting Convert->Maven Project.

On IntelliJ, we can do this by simply clicking on the save as Maven Project file while importing the project.
	
After creating the Maven project, add the following snippet to the auto-generated pom.xml file (the file with the required changes has also been included in the project files)

```xml
<dependency>
   <groupId>com.github.javafaker</groupId>
   <artifactId>javafaker</artifactId>
   <version>0.15</version>
</dependency>
```

To execute the file, build the project on your respective IDE and then click on Run. The code should run with auto-generated data from Java Faker.
