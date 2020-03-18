##### Notes:

There are some aspects that can be improved for this project. I didn't do them because of
 time constraints but I would like to write them out for awareness.
 
 Technical improvements:
 1. Use docker to manage dependencies (MYSQL)
 2. Store item's images in a bucket using AWS S3 or Google Cloud Storage
 3. Add more validation to the endpoints to prevent invalid data and injection attacks
 4. Thymeleaf templates could be improved to have more reusable fragments. 
 
 Regarding the UI improvements, it can be improved a lot by making it look better and user friendly, plus making sure
 it is fully responsive. 
 
 ##### Initializing dependencies
 The application requires a MySQL database with the schema called 'marketplace', with host 'localhost' and port '3306'.
 These properties can be configured in the application.properties file. 
 
 The database can be set up using the following commands:
 
 CREATE SCHEMA `marketplace`;
 
 USE `marketplace`;
 
 Once the schema is created, the application will create the tables when it is executed.
 
 
 ##### Running the application:
 To run the application a gradle build must be executed followed by executing the app using the following commands:
 
 1. ./gradlew build
 2. ./gradlew bootRun
 
 Or it can be imported as a project in IntelliJ and ran by executing the main function in MarketplaceApplication.kt 
 since it is a spring boot application.
 
 For the first time, once the database schema is created and the application is already running, you can seed some 
 users into the database running the following SQL statement:
 
 INSERT INTO `users` VALUES
 ('1', 'user1fn', 'user1ln', '$2y$12$3IW8qr5Ghtam9qmoWXboeOft4G8yiDMUFW/iakcFJL8Pc.BbkwDeC' , 'user1'),
 ('2', 'user2fn', 'user2ln', '$2y$12$3IW8qr5Ghtam9qmoWXboeOft4G8yiDMUFW/iakcFJL8Pc.BbkwDeC' , 'user2'),
 ('3', 'user3fn', 'user3ln', '$2y$12$3IW8qr5Ghtam9qmoWXboeOft4G8yiDMUFW/iakcFJL8Pc.BbkwDeC' , 'user3');
 
 The fourth parameter value is the password for each user, which is encrypted using BCrypt. The one used above 
 represents the password: 'password', but you can use an online generator to generate another one such as 
 https://bcrypt-generator.com/ 
 
 ##### Using the application:
 
 The application is by default running on localhost:8080, which redirects to the login page. Once logged in, there
 is navigation to go to the different pages to view items on sale, view personal items on sale, view the shopping cart
 and add a new item for sale. 
 
 ##### Questions
 For any help needed you can message me to jolesa97@gmail.com
