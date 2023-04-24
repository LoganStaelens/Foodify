CREATE TABLE IF NOT EXISTS Country (
    name varchar(128),
    PRIMARY KEY (Name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS City (
    city_id INT NOT NULL AUTO_INCREMENT,
    country VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    postCode VARCHAR(128) NOT NULL,
    PRIMARY KEY(city_id),
    FOREIGN KEY(country) REFERENCES Country(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Address (
    address_id INT NOT NULL AUTO_INCREMENT,
    city INT NOT NULL,
    street VARCHAR(128) NOT NULL,
    number INT NOT NULL,
    PRIMARY KEY (address_id),
    FOREIGN KEY(city) REFERENCES City(city_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Gender (
    gender CHAR NOT NULL,
    PRIMARY KEY(gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS User (
    unique_id INT NOT NULL AUTO_INCREMENT,
    address INT NOT NULL,
    gender CHAR NOT NULL,
    isAdmin BOOLEAN NOT NULL,
    firstName VARCHAR(64) NOT NULL,
    lastName VARCHAR(64) NOT NULL,
    email VARCHAR(128) NOT NULL,
    birthDate DATE NOT NULL,
    phoneNumber VARCHAR(32),
    PRIMARY KEY(unique_id),
    FOREIGN KEY(address) REFERENCES Address(address_id),
    FOREIGN KEY(gender) REFERENCES Gender(gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Menu (
    menu_id INT NOT NULL AUTO_INCREMENT,
    user INT NOT NULL,
    year INT NOT NULL,
    week INT NOT NULL,
    PRIMARY KEY (menu_id),
    FOREIGN KEY(user) REFERENCES User(unique_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Subscription (
    subscription_id INT NOT NULL AUTO_INCREMENT,
    user INT NOT NULL,
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    PRIMARY KEY (subscription_id),
    FOREIGN KEY (user) REFERENCES User(unique_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Invoice (
    invoice_number INT NOT NULL AUTO_INCREMENT,
    subscription INT NOT NULL,
    exclVATTotal DECIMAL(4, 2) NOT NULL,
    inclVATTotal DECIMAL(4, 2) NOT NULL,
    isPaid BOOLEAN NOT NULL,
    PRIMARY KEY (invoice_number),
    FOREIGN KEY (subscription) REFERENCES Subscription(subscription_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Complexity (
    complexity VARCHAR(32) NOT NULL,
    PRIMARY KEY (complexity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Recipe (
    recipe_id INT NOT NULL AUTO_INCREMENT,
    complexity VARCHAR(32) NOT NULL,
    isVisible BOOLEAN NOT NULL,
    PRIMARY KEY (recipe_id),
    FOREIGN KEY (complexity) REFERENCES Complexity(complexity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS MenuItem (
    menu_item_id INT NOT NULL AUTO_INCREMENT,
    menu INT NOT NULL,
    recipe INT NOT NULL,
    day INT NOT NULL,
    PRIMARY KEY (menu_item_id),
    FOREIGN KEY (menu) REFERENCES Menu(menu_id),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS RecipeStep (
    recipe_step_id INT NOT NULL AUTO_INCREMENT,
    recipe INT NOT NULL,
    stepCount INT NOT NULL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(2048) NOT NULL,
    duration INT NOT NULL,
    PRIMARY KEY (recipe_step_id),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Tag (
    tag_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS TagLink (
    recipe INT NOT NULL,
    tag INT NOT NULL,
    PRIMARY KEY (recipe, tag),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id),
    FOREIGN KEY (tag) REFERENCES Tag(tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Unit (
    name VARCHAR(64) NOT NULL,
    abbreviation VARCHAR(4) NOT NULL,
    PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Ingredient (
    ingredient_id INT NOT NULL AUTO_INCREMENT,
    unit VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    calories INT NOT NULL,
    PRIMARY KEY (ingredient_id),
    FOREIGN KEY (unit) REFERENCES Unit(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS IngredientStack (
    recipe INT NOT NULL,
    ingredient INT NOT NULL,
    PRIMARY KEY (recipe, ingredient),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id),
    FOREIGN KEY (ingredient) REFERENCES Ingredient(ingredient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;