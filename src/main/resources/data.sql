DROP TABLE IF EXISTS Department;

DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  phone VARCHAR(13) DEFAULT NULL,
  created_by VARCHAR(250) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_by VARCHAR(250) NULL,
  updated_on TIMESTAMP
);



CREATE TABLE public.Department
(
    id integer NOT NULL,
    name character varying(250)  NOT NULL,
    description character varying(250)  NOT NULL,
    created_by character varying(250)  NOT NULL,
    created_on timestamp without time zone NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id),
    CONSTRAINT fk_employee FOREIGN KEY(id) REFERENCES employee(id)
);


INSERT INTO Employee (name,first_name,last_name, email, phone, created_by, created_on) VALUES
  ('RAM KUMAR', 'ram','kumar', 'ram@evoketechnologies.com', '0000000000', 'system',now()),
  ('GOPAL KRISHNA','gopal','krishna', 'gopal@evoketechnologies.com', '1111111111', 'system',now()),
  ('SREE NIVAS','sree','nivas', 'sree@evoketechnologies.com', '2222222222', 'system',now()),
  ('VISHNU VARMA','vishnu','varma', 'vishnu@evoketechnologies.com', '3333333333', 'system',now());
  
  INSERT INTO public.department(
	id, name, description, created_by, created_on)
	VALUES (1, 'IT', 'Information Of technology', 'system', now()),
	 (2, 'IT', 'Information Of technology', 'system', now()),
	 (3, 'HR', 'Human Resource', 'system', now()),
	 (4, 'TAG', 'Tech Analysis Group', 'system', now());