CREATE TABLE person
  (
     id  BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)  PRIMARY KEY ,
     first_name       VARCHAR(255) NOT NULL,
     last_name        VARCHAR(255) NOT NULL,
     age   INTEGER NOT NULL ,
     favorite_colour VARCHAR(255),
     CONSTRAINT UC_person UNIQUE (first_name,last_name,age)
  );

  insert into person (age, favorite_colour, first_name, last_name) values (10, 'red', 'fazeem', 'mohammed');
