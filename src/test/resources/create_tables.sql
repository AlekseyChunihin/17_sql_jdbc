Create table if not exists role_table
(
role_id int PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
);

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
);
