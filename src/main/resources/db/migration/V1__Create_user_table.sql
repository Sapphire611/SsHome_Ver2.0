CREATE TABLE user(
 id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
 accountid varchar(100),
 name varchar(50),
 token varchar(36),
 gmtCreate BIGINT,
 gmtModified BIGINT
);