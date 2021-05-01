DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  phone VARCHAR(13) DEFAULT NULL,
  created_by VARCHAR(250) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_by VARCHAR(250) NULL,
  updated_on TIMESTAMP
);

INSERT INTO Employee (name, email, phone, created_by, created_on) VALUES
  ('Ram', 'ram@evoketechnologies.com', '0000000000', 'system',now()),
  ('gopal', 'gopal@evoketechnologies.com', '1111111111', 'system',now()),
  ('srinivas', 'sree@evoketechnologies.com', '2222222222', 'system',now()),
  ('vishnu', 'vishnu@evoketechnologies.com', '3333333333', 'system',now())