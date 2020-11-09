# Database
Hey all! Here is a breakdown on what the Database contains. If you want to run this in Eclipse, you will need to add the JDBC jar file that we needed for lab 10.

**db_connect.java**  
The equivalent of a Util class that will allow you to do any operations you would need the database for (i.e. you should not need to write any code to connect to the database yourself, I wrote it for you!). **Note**: it is good practice to check if *userExists* before using any of the other functions.
- *addUser*: Creates a new user in the database
- *userExists*: Returns a boolean for whether a user exists or not
- *validateLogin*: Returns true if the username and password combonation are correct, false otherwise
- *addWin, addLoss*: Adds a win/loss to the specified user
- *getWins, getLosses*: Returns the total wins/losses of the specified user

**example.java**  
A list of examples for how you can use the functions of db_connect and what exceptions you will need to handle.

**tester.java**  
A list of tests for db_connect.

**uno.sql**  
Runnable SQL file to create the database.
