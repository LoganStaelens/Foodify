CREATE TABLE Country (
    name varchar(128),
    PRIMARY KEY (Name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE City (
    city_id INT NOT NULL AUTO_INCREMENT,
    country VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    postCode VARCHAR(128) NOT NULL,
    PRIMARY KEY(city_id),
    FOREIGN KEY(country) REFERENCES Country(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Address (
    address_id INT NOT NULL AUTO_INCREMENT,
    city INT NOT NULL,
    street VARCHAR(128) NOT NULL,
    number INT NOT NULL,
    PRIMARY KEY (address_id),
    FOREIGN KEY(city) REFERENCES City(city_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

CREATE TABLE Gender (
    gender CHAR NOT NULL,
    PRIMARY KEY(gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE User (
    unique_id INT NOT NULL AUTO_INCREMENT,
    address INT NOT NULL,
    gender CHAR NOT NULL,
    isAdmin BOOLEAN NOT NULL,
    firstName VARCHAR(64) NOT NULL,
    lastName VARCHAR(64) NOT NULL,
    email VARCHAR(128),
    birthDate DATE NOT NULL,
    phoneNumber VARCHAR(32),
    PRIMARY KEY(unique_id),
    FOREIGN KEY(address) REFERENCES Address(address_id),
    FOREIGN KEY(gender) REFERENCES Gender(gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO Country(name) VALUES ("Belgique");

INSERT INTO City (country, name, postCode) VALUES ("Belgique", "Namur", "5000");
INSERT INTO City (country, name, postCode) VALUES ("Belgique", "Rendeux", "6987");
INSERT INTO City (country, name, postCode) VALUES ("Belgique", "Chatelet", "6200");