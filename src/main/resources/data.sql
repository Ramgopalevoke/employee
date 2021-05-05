DELETE FROM Employee;

DROP TABLE IF EXISTS Employee;

DROP TABLE IF EXISTS Department;


CREATE TABLE public.Department
(
    dep_id INT AUTO_INCREMENT PRIMARY KEY,
    dep_name character varying(250)  NOT NULL,
    description character varying(250)  NOT NULL,
    created_by character varying(250)  NOT NULL,
    created_on timestamp without time zone NOT NULL
  
    
);


CREATE TABLE Employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  dep_id INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  phone VARCHAR(13) DEFAULT NULL,
  doj TIMESTAMP NOT NULL,
  created_by VARCHAR(250) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_by VARCHAR(250) NULL,
  updated_on TIMESTAMP ,
  CONSTRAINT fk_employee FOREIGN KEY(dep_id) REFERENCES department(dep_id)
);


 INSERT INTO public.department(
	 dep_name, description, created_by, created_on)
	VALUES ( 'IT', 'Information Of technology', 'system', now()),
	 ('HR', 'Human Resource', 'system', now()),
	 ('TAG', 'Tech Analysis Group', 'system', now());


INSERT INTO Employee (dep_id, name,first_name,last_name, email, phone,doj, created_by, created_on) VALUES
  (1, 'RAM KUMAR', 'ram','kumar', 'ram@evoketechnologies.com', '0000000000',now(), 'system',now()),
  (1, 'GOPAL KRISHNA','gopal','krishna', 'gopal@evoketechnologies.com', '1111111111',now(), 'system',now()),
  (2, 'SREE NIVAS','sree','nivas', 'sree@evoketechnologies.com', '2222222222',now(), 'system',now()),
  (3, 'VISHNU VARMA','vishnu','varma', 'vishnu@evoketechnologies.com', '3333333333', now(),'system',now());
  
 