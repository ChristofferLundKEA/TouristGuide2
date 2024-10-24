-- Opret databasen
CREATE SCHEMA TouristGuideD3;

-- Skift til den nye database
USE TouristGuideD3;

-- Opret tabel for City
CREATE TABLE City (
cityID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR(100) NOT NULL
);

-- Opret tabel for Attraction
CREATE TABLE Attraction (
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
cityID INT NOT NULL,
description VARCHAR(255),
fee INT NOT NULL,
FOREIGN KEY (cityID) REFERENCES City(cityID)
);

-- Opret tabel for Tag
CREATE TABLE Tag (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
UNIQUE INDEX (name)
);

-- Opret krydstabel for Attraction:tag (mange-til-mange forhold(ER-model))
CREATE TABLE Attraction_tag (
attraction_id INT NOT NULL,
tag_id INT NOT NULL,
PRIMARY KEY (attraction_id, tag_id),
FOREIGN KEY (attraction_id) REFERENCES Attraction(id),
FOREIGN KEY (tag_id) REFERENCES Tag(id)
);