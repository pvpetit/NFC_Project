--
-- File generated with SQLiteStudio v3.2.1 on Thu Oct 31 11:56:58 2019
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Employee
CREATE TABLE Employee (
    cardID    VARCHAR (14) PRIMARY KEY
                           UNIQUE
                           NOT NULL,
    firstName VARCHAR (20) NOT NULL,
    lastName  VARCHAR (20) NOT NULL,
    age       INTEGER      NOT NULL,
    username  VARCHAR (8)  UNIQUE
                           NOT NULL,
    password  VARCHAR (8)  NOT NULL
);
INSERT INTO Employee (cardID, firstName, lastName, age, username, password) VALUES ('04CC9C72816380', 'Juan', 'Ariza', 21, 'jariza98', '12341234');
INSERT INTO Employee (cardID, firstName, lastName, age, username, password) VALUES ('0419897A816381', 'Philemon', 'Petit-Frere', 21, 'ppetit34', 'asdfzxcv');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
