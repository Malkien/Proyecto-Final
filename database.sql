DROP USER if exists 'adminchatbot';
CREATE USER adminchatbot  IDENTIFIED BY 'admin';

CREATE DATABASE chatbot CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

COMMIT;

CREATE TABLE IF NOT EXISTS chatbot.user(
	username varchar(20) NOT NULL,
    password varbinary(50) NOT NULL,
    email varchar(50) PRIMARY KEY,
    birthday date NOT NULL,
    question varchar(100) NOT NULL,
    answer varchar(100) NOT NULL
);

GRANT SELECT, INSERT, UPDATE ON chatbot.* TO 'adminchatbot';

FLUSH PRIVILEGES;
COMMIT;
