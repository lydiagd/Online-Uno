DROP DATABASE IF EXISTS uno;
CREATE DATABASE uno;
USE uno;

CREATE TABLE Users (
    ID INT NOT NULL AUTO_INCREMENT,
    Username VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Wins INT,
    Losses INT,
    PRIMARY KEY (ID)
);

INSERT INTO Users (Username, Password, Wins, Losses)
VALUES ("Sanity Check", "badPassword", 0, 0);