CREATE USER adminChatBot  IDENTIFIED BY 'admin';

CREATE DATABASE chatbot;

GRANT ALL PRIVILEGES ON `chatbot` TO adminChatBot;

FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS userEnterprise(
	username varchar(20) NOT NULL,
    password varchar(50) NOT NULL,
    email varchar(50) PRIMARY KEY,
    birthday date NOT NULL,
    question varchar(100) NOT NULL,
    answer varchar(100) NOT NULL
)