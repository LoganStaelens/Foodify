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

CREATE TABLE Menu (
    menu_id INT NOT NULL AUTO_INCREMENT,
    user INT NOT NULL,
    year INT NOT NULL,
    week INT NOT NULL,
    PRIMARY KEY (menu_id),
    FOREIGN KEY(user) REFERENCES User(unique_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Subscription (
    subscription_id INT NOT NULL AUTO_INCREMENT,
    user INT NOT NULL,
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    PRIMARY KEY (subscription_id),
    FOREIGN KEY (user) REFERENCES User(unique_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Invoice (
    invoice_number INT NOT NULL AUTO_INCREMENT,
    subscription INT NOT NULL,
    exclVATTotal DECIMAL(4, 2) NOT NULL,
    inclVATTotal DECIMAL(4, 2) NOT NULL,
    isPaid BOOLEAN NOT NULL,
    PRIMARY KEY (invoice_number),
    FOREIGN KEY (subscription) REFERENCES Subscription(subscription_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Complexity (
    complexity VARCHAR(32) NOT NULL,
    PRIMARY KEY (complexity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Recipe (
    recipe_id INT NOT NULL AUTO_INCREMENT,
    complexity VARCHAR(32) NOT NULL,
    isVisible BOOLEAN NOT NULL,
    PRIMARY KEY (recipe_id),
    FOREIGN KEY (complexity) REFERENCES Complexity(complexity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE MenuItem (
    menu_item_id INT NOT NULL AUTO_INCREMENT,
    menu INT NOT NULL,
    recipe INT NOT NULL,
    day INT NOT NULL,
    PRIMARY KEY (menu_item_id),
    FOREIGN KEY (menu) REFERENCES Menu(menu_id),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE RecipeStep (
    recipe_step_id INT NOT NULL AUTO_INCREMENT,
    recipe INT NOT NULL,
    stepCount INT NOT NULL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(2048) NOT NULL,
    duration INT NOT NULL,
    PRIMARY KEY (recipe_step_id),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Tag (
    tag_id INT NOT NULL AUTO_INCREMENT,
    recipe INT NOT NULL,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (tag_id),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Unit (
    name VARCHAR(64) NOT NULL,
    abbreviation VARCHAR(4),
    PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Ingredient (
    ingredient_id INT NOT NULL AUTO_INCREMENT,
    unit VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    calories INT NOT NULL,
    PRIMARY KEY (ingredient_id),
    FOREIGN KEY (unit) REFERENCES Unit(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IngredientStack (
    recipe INT NOT NULL,
    ingredient INT NOT NULL,
    PRIMARY KEY (recipe, ingredient),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id),
    FOREIGN KEY (ingredient) REFERENCES Ingredient(ingredient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;