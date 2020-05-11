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

CREATE TABLE IF NOT EXISTS chatbot.api(
	id int PRIMARY KEY auto_increment,
    name varchar(50) UNIQUE NOT NULL,
	apiKey varbinary(100) NOT NULL,
    apiId varbinary(100) NOT NULL,
    email varchar(50) NOT NULL,
    foreign key (email) references user(email) 
);
INSERT INTO chatbot.user VALUES(
'kevin',
aes_encrypt('Kicsnet9*', 'kevin@kevin.com'),
'kevin@kevin.com',date('2020-01-14') ,
"What was your childhood's pet?",
'Toby'
);
INSERT INTO chatbot.api(name, apiKey, apiId, email) VALUES( 
'prueba2', 
aes_encrypt('YJmV5K8mOWHPTUmTWTwO_U0v9PhPVvPlcfexgD2N6E1D', 'kevin@kevin.com'), 
aes_encrypt('https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/34a99098-ec05-430b-92cd-43b5508e229a', 'kevin@kevin.com'), 
'kevin@kevin.com'
);
GRANT SELECT, INSERT, UPDATE ON chatbot.* TO 'adminchatbot';
FLUSH PRIVILEGES;
COMMIT;