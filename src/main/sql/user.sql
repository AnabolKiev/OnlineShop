DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  firstName TEXT,
  lastName TEXT,
  salary REAL,
  dateOfBirth TEXT
);

INSERT INTO user (firstName, lastName, salary, dateOfBirth) VALUES ('Donald', 'Trump', 1000.00, '1965-07-29');
INSERT INTO user (firstName, lastName, salary, dateOfBirth) VALUES ('Vladimir', 'Putin', 799.99, '1955-08-30');

SELECT * FROM user;