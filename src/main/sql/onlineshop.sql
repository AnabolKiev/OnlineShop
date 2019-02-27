CREATE SCHEMA `onlinestore` ;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
  name VARCHAR(30) PRIMARY KEY,
  password VARCHAR(76),
  salt VARCHAR(76),
  role VARCHAR(20)
);

INSERT INTO user (name, password, salt, role) VALUES ('Anabol', 'OWfxra2qW5bnpaOLRXyNPbIONlI=', 'UgqCz8+Gl7lr5MqZcmhyFQ==', 'ADMIN');
UPDATE  user SET role = 'ADMIN' where name = 'Anabol';
commit;

select * from user;


DROP TABLE IF EXISTS product;

CREATE TABLE product (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30),
  description VARCHAR(250),
  price DOUBLE
);

INSERT INTO product (name, description, price) VALUES ('Iphone 4', 'Vintage smartphone from Apple', 200.00);
INSERT INTO product (name, description, price) VALUES ('Iphone X', 'The newest smartphone from Apple', 999.99);
commit;

SELECT * FROM product;