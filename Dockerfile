FROM openjdk:8-jre-alpine
<<<<<<< HEAD
EXPOSE 8084
ADD target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1.0.jar timesheet_devops.jar
=======
EXPOSE 8083
ADD target/timesheet_devops-1.0.jar timesheet_devops.jar
>>>>>>> Sahar
ENTRYPOINT ["java", "-jar", "/timesheet_devops.jar"] 
