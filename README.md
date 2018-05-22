#Installation

1. install postgresql
2. create a postgresql database called home.ht with the userrole of the logged in user.

   (optionally uncomment the last two lines of application.conf and specify a username and
   password in the application.conf)

3. make shure the datebase is located at localhost:5432

4. download the project from github
5. import project with intellij (maven)
6. download and enable Lombock plugin

7. start the configuration with com.homeht.CodeChallengeApplication as MainClass
8. the payments table for the database will be created automatically
9. if the table is empty, the programm will fill it with some test data \
 (contract 1 and 2 with some payments)
