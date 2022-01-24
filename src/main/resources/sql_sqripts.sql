Create table if not exists role_table
(
role_id int PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
)

CREATE TABLE IF NOT EXISTS user_table (
  user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  birthday DATE,
  role_id BIGINT,
  foreign key (role_id) references role_table (role_id)
)

INSERT INTO USER_TABLE (login, password, email, first_name, last_name, birthday, role_id) VALUES ('login1', 'password1', 'email1','firstname1','lastname1','2003-03-31',1);
INSERT INTO USER_TABLE (login, password, email, first_name, last_name, birthday, role_id) VALUES ('login2', 'password2', 'email2','firstname2','lastname2','1996-03-23',1);
INSERT INTO USER_TABLE (login, password, email, first_name, last_name, birthday, role_id) VALUES ('login3', 'password3', 'email3','firstname3','lastname3','1976-11-11',2);


INSERT INTO role_table (name) VALUES ('role1');
INSERT INTO role_table (name) VALUES ('role2');

DROP TABLE USER_TABLE;

DROP TABLE ROLE_TABLE;
