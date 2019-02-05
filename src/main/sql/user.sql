CREATE SCHEMA `onlinestore` ;

DROP TABLE IF EXISTS onlinestore.user;

CREATE TABLE onlinestore.user (
  name VARCHAR(30) PRIMARY KEY,
  password VARCHAR(30),
  role VARCHAR(20)
);

INSERT INTO onlinestore.user (name, password, role) VALUES ('Anabol', '12345', 'ADMIN');



DROP TABLE IF EXISTS onlinestore.product;

CREATE TABLE onlinestore.product (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30),
  description VARCHAR(250),
  price DOUBLE
);

INSERT INTO onlinestore.product (name, description, price) VALUES ('Iphone 4', 'Vintage smarthone from Apple', 200.00);
INSERT INTO onlinestore.product (name, description, price) VALUES ('Iphone X', 'The newest smarthone from Apple', 999.99);

SELECT * FROM onlinestore.product;