DROP TABLE USERS if exists;
DROP TABLE ROLES if exists;
DROP TABLE TOKEN if exists;
DROP TABLE USERS_AUTHORITY if exists;

CREATE TABLE USERS
 (      ID INTEGER PRIMARY KEY,
        LOGINID VARCHAR(20) NOT NULL ,
        FIRST_NAME VARCHAR(50) NOT NULL ,
        FAMILY_NAME VARCHAR(50),
        EMAIL VARCHAR(50) NOT NULL ,
        PASSWORD VARCHAR(50),
        ENABLED VARCHAR(5)
);

CREATE TABLE ROLES
(
        ID INTEGER PRIMARY KEY,
        NAME VARCHAR(30) NOT NULL ,
        DESCRIPTION VARCHAR(100)
);


CREATE TABLE TOKEN
(       SERIES VARCHAR(50) NOT NULL ,
        VALUE VARCHAR(20),
        CREATED_DATE TIMESTAMP (6),
        IP_ADDRESS VARCHAR(50),
        USER_LOGIN VARCHAR(50) NOT NULL,
        USER_AGENT VARCHAR(200)
);


CREATE TABLE USERS_AUTHORITY
(       ID INTEGER PRIMARY KEY,
        USER_ID INTEGER,
        ROLE_ID INTEGER
);