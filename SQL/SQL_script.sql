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
    unique_id VARCHAR(36) NOT NULL,
    address INT NOT NULL,
    gender CHAR NOT NULL,
    isAdmin BOOLEAN NOT NULL,
    firstName VARCHAR(64) NOT NULL,
    lastName VARCHAR(64) NOT NULL,
    email VARCHAR(128) NOT NULL,
    password VARCHAR(64) NOT NULL,
    birthDate DATE NOT NULL,
    phoneNumber VARCHAR(32),
    PRIMARY KEY(unique_id),
    FOREIGN KEY(address) REFERENCES Address(address_id),
    FOREIGN KEY(gender) REFERENCES Gender(gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Menu (
    menu_id INT NOT NULL AUTO_INCREMENT,
    user VARCHAR(36) NOT NULL,
    year INT NOT NULL,
    week INT NOT NULL,
    PRIMARY KEY (menu_id),
    FOREIGN KEY(user) REFERENCES User(unique_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Complexity (
    complexity VARCHAR(32) NOT NULL,
    degree INT NOT NULL,
    PRIMARY KEY (complexity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Recipe (
    recipe_id INT NOT NULL AUTO_INCREMENT,
    complexity VARCHAR(32) NOT NULL,
    isVisible BOOLEAN NOT NULL,
    title VARCHAR(64) NOT NULL,
    lastUpdate DATETIME NOT NULL,
    creatorFirstName VARCHAR(64),
    creatorLastName VARCHAR(64),
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
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS TagLink (
    recipe INT NOT NULL,
    tag VARCHAR(64) NOT NULL,
    PRIMARY KEY (recipe, tag),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id),
    FOREIGN KEY (tag) REFERENCES Tag(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Unit (
    unit_id INT NOT NULL,
    name VARCHAR(64) NOT NULL,
    abbreviation VARCHAR(4) NOT NULL,
    PRIMARY KEY (unit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS Ingredient (
    ingredient_id INT NOT NULL AUTO_INCREMENT,
    unit INT NOT NULL,
    name VARCHAR(64) NOT NULL,
    calories INT NOT NULL,
    PRIMARY KEY (ingredient_id),
    FOREIGN KEY (unit) REFERENCES Unit(unit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS IngredientStack (
    recipe INT NOT NULL,
    ingredient INT NOT NULL,
    amount INT NOT NULL,
    PRIMARY KEY (recipe, ingredient),
    FOREIGN KEY (recipe) REFERENCES Recipe(recipe_id),
    FOREIGN KEY (ingredient) REFERENCES Ingredient(ingredient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO complexity (complexity, degree) VALUES 
("DEBUTANT", 0),
("FACILE", 1),
("MOYEN", 2),
("DIFFICILE", 3),
("PRO", 4);


INSERT INTO gender (gender) VALUES 
('m'),
('f'),
('x');

INSERT INTO country (name) VALUES
('Afghanistan'),
('Albanie'),
('Antarctique'),
('Algérie'),
('Samoa Américaines'),
('Andorre'),
('Angola'),
('Antigua-et-Barbuda'),
('Azerbaïdjan'),
('Argentine'),
('Australie'),
('Autriche'),
('Bahamas'),
('Bahreïn'),
('Bangladesh'),
('Arménie'),
('Barbade'),
('Belgique'),
('Bermudes'),
('Bhoutan'),
('Bolivie'),
('Bosnie-Herzégovine'),
('Botswana'),
('Île Bouvet'),
('Brésil'),
('Belize'),
('Territoire Britannique de l''Océan Indien'),
('Îles Salomon'),
('Îles Vierges Britanniques'),
('Brunéi Darussalam'),
('Bulgarie'),
('Myanmar'),
('Burundi'),
('Bélarus'),
('Cambodge'),
('Cameroun'),
('Canada'),
('Cap-vert'),
('Îles Caïmanes'),
('République Centrafricaine'),
('Sri Lanka'),
('Tchad'),
('Chili'),
('Chine'),
('Taïwan'),
('Île Christmas'),
('Îles Cocos (Keeling)'),
('Colombie'),
('Comores'),
('Mayotte'),
('République du Congo'),
('République Démocratique du Congo'),
('Îles Cook'),
('Costa Rica'),
('Croatie'),
('Cuba'),
('Chypre'),
('République Tchèque'),
('Bénin'),
('Danemark'),
('Dominique'),
('République Dominicaine'),
('Équateur'),
('El Salvador'),
('Guinée Équatoriale'),
('Éthiopie'),
('Érythrée'),
('Estonie'),
('Îles Féroé'),
('Îles (malvinas) Falkland'),
('Géorgie du Sud et les Îles Sandwich du Sud'),
('Fidji'),
('Finlande'),
('Îles Åland'),
('France'),
('Guyane Française'),
('Polynésie Française'),
('Terres Australes Françaises'),
('Djibouti'),
('Gabon'),
('Géorgie'),
('Gambie'),
('Territoire Palestinien Occupé'),
('Allemagne'),
('Ghana'),
('Gibraltar'),
('Kiribati'),
('Grèce'),
('Groenland'),
('Grenade'),
('Guadeloupe'),
('Guam'),
('Guatemala'),
('Guinée'),
('Guyana'),
('Haïti'),
('Îles Heard et Mcdonald'),
('Saint-Siège (état de la Cité du Vatican)'),
('Honduras'),
('Hong-Kong'),
('Hongrie'),
('Islande'),
('Inde'),
('Indonésie'),
('République Islamique d''Iran'),
('Iraq'),
('Irlande'),
('Israël'),
('Italie'),
('Côte d''Ivoire'),
('Jamaïque'),
('Japon'),
('Kazakhstan'),
('Jordanie'),
('Kenya'),
('République Populaire Démocratique de Corée'),
('République de Corée'),
('Koweït'),
('Kirghizistan'),
('République Démocratique Populaire Lao'),
('Liban'),
('Lesotho'),
('Lettonie'),
('Libéria'),
('Jamahiriya Arabe Libyenne'),
('Liechtenstein'),
('Lituanie'),
('Luxembourg'),
('Macao'),
('Madagascar'),
('Malawi'),
('Malaisie'),
('Maldives'),
('Mali'),
('Malte'),
('Martinique'),
('Mauritanie'),
('Maurice'),
('Mexique'),
('Monaco'),
('Mongolie'),
('République de Moldova'),
('Montserrat'),
('Maroc'),
('Mozambique'),
('Oman'),
('Namibie'),
('Nauru'),
('Népal'),
('Pays-Bas'),
('Antilles Néerlandaises'),
('Aruba'),
('Nouvelle-Calédonie'),
('Vanuatu'),
('Nouvelle-Zélande'),
('Nicaragua'),
('Niger'),
('Nigéria'),
('Niué'),
('Île Norfolk'),
('Norvège'),
('Îles Mariannes du Nord'),
('Îles Mineures Éloignées des États-Unis'),
('États Fédérés de Micronésie'),
('Îles Marshall'),
('Palaos'),
('Pakistan'),
('Panama'),
('Papouasie-Nouvelle-Guinée'),
('Paraguay'),
('Pérou'),
('Philippines'),
('Pitcairn'),
('Pologne'),
('Portugal'),
('Guinée-Bissau'),
('Timor-Leste'),
('Porto Rico'),
('Qatar'),
('Réunion'),
('Roumanie'),
('Fédération de Russie'),
('Rwanda'),
('Sainte-Hélène'),
('Saint-Kitts-et-Nevis'),
('Anguilla'),
('Sainte-Lucie'),
('Saint-Pierre-et-Miquelon'),
('Saint-Vincent-et-les Grenadines'),
('Saint-Marin'),
('Sao Tomé-et-Principe'),
('Arabie Saoudite'),
('Sénégal'),
('Seychelles'),
('Sierra Leone'),
('Singapour'),
('Slovaquie'),
('Viet Nam'),
('Slovénie'),
('Somalie'),
('Afrique du Sud'),
('Zimbabwe'),
('Espagne'),
('Sahara Occidental'),
('Soudan'),
('Suriname'),
('Svalbard etÎle Jan Mayen'),
('Swaziland'),
('Suède'),
('Suisse'),
('République Arabe Syrienne'),
('Tadjikistan'),
('Thaïlande'),
('Togo'),
('Tokelau'),
('Tonga'),
('Trinité-et-Tobago'),
('Émirats Arabes Unis'),
('Tunisie'),
('Turquie'),
('Turkménistan'),
('Îles Turks et Caïques'),
('Tuvalu'),
('Ouganda'),
('Ukraine'),
('L''ex-République Yougoslave de Macédoine'),
('Égypte'),
('Royaume-Uni'),
('Île de Man'),
('République-Unie de Tanzanie'),
('États-Unis'),
('Îles Vierges des États-Unis'),
('Burkina Faso'),
('Uruguay'),
('Ouzbékistan'),
('Venezuela'),
('Wallis et Futuna'),
('Samoa'),
('Yémen'),
('Serbie-et-Monténégro'),
('Zambie');

INSERT INTO tag (name) VALUES
("VEGETARIEN"),
("VEGAN"),
("VIANDE"),
("POISSON"),
("COREEN"),
("JAPONAIS"),
("CHINOIS"),
("FRANCAIS"),
("PEU CALORIQUE"),
("SANS PORC"),
("HALLAL"),
("ITALIEN");

INSERT INTO unit (unit_id, name, abbreviation) VALUES 
(1, "Gramme", "g"),
(2, "CuillèreASoupe", "càs"),
(3, "CuillèreACafé", "càc"),
(4, "millilitre", "ml"),
(5, "Verre", ""),
(6, "Quantitée", "");

INSERT INTO city(country, name, postCode) VALUES
("Belgique", "Rendeux", "6987");

INSERT INTO address(city, street, number) VALUES
(1, "Le Belvedere", 13);

INSERT INTO user(unique_id, address, gender, isAdmin, firstName, lastName, email, password, birthDate) VALUES
("94c535ba-40ac-4910-ba34-f63bfa896494", 1, 'x', TRUE, "admin", "admin", "admin@foodify.com", "d2152cca1d8c50aafe1e82dab574348b9f602a394b7ec4f470a0c0f9dd735d04", NOW());


--Tout les clories sont sur une base de 100gr

--fruits
INSERT INTO Ingredient (unit, name, calories) VALUES
(1, "Abricot", 48),
(1, "Ananas", 50),
(1, "Avocat", 160),
(1, "Banane", 89),
(1, "Cerises", 50),
(1, "Citron", 29),
(1, "Clémentine", 47),
(1, "Compote de pommes", 68),
(1, "Dattes", 282),
(1, "Figues", 74),
(1, "Fraises", 32),
(1, "Framboises", 52),
(1, "Fruit de la passion", 97),
(1, "Grenade", 83),
(1, "Groseilles", 56),
(1, "Kaki", 127),
(1, "Kiwi", 61),
(1, "Lime", 30),
(1, "Litchis", 66),
(1, "Mandarines", 53),
(1, "Mangue", 60),
(1, "Melon cantaloup", 34),
(1, "Myrtilles", 57),
(1, "Mûres", 43),
(1, "Nectarine", 44),
(1, "Olives", 115),
(1, "Orange", 47),
(1, "Orange Sanguine", 50),
(1, "Papaye", 43),
(1, "Pastèque", 30),
(1, "Physalis", 49),
(1, "Poire", 57),
(1, "Pomme", 52),
(1, "Prune", 46),
(1, "Pêche", 39),
(1, "Raisins", 69),
(1, "Raisins Secs", 299),
(1, "Ramboutan", 82),
(1, "Reine-clause", 41),
(1, "Rhubarbe", 21),
(1, "Melon", 34),
(1, "Melon Charentais", 34),
(1, "Pamplemousse", 42),
(1, "Pamplemousse Rose", 42),
(1, "Pomélo", 38),
(1, "Aïl", 34),
(1, "Artichaut", 47),
(1, "Asperge", 20),
(1, "Aubergine", 25),
(1, "Bette", 19),
(1, "Betterave", 43),
(1, "Brocoli", 34),
(1, "Carotte", 41),
(1, "Champignons", 22),
(1, "Chicorée", 72),
(1, "Chou", 25),
(1, "Chou Cavalier", 32),
(1, "Chou Chinois", 16),
(1, "Chou Fleur", 25),
(1, "Chou Frisé", 49),
(1, "Chou Rave", 16),
(1, "Chou Rouge", 31),
(1, "Choux de Bruxelles", 43),
(1, "Ciboulette", 30),
(1, "Concombre", 16),
(1, "Cornichon", 14),
(1, "Courge", 14),
(1, "Courgette", 17),
(1, "Céléri", 16),
(1, "Chicon", 17),
(1, "Fanes de Navets", 200),
(1, "Fenouil", 31),
(1, "Haricots-verts", 31),
(1, "Laitue", 15),
(1, "Maïs", 365),
(1, "Moutarde Brune", 27),
(1, "Navets", 28),
(1, "Nori", 35),
(1, "Oignon", 40),
(1, "Oignon Vert", 32),
(1, "Olives", 115),
(1, "Olives Noires", 115),
(1, "Olives Vertes", 115),
(1, "Panais", 75),
(1, "Patate Douce", 86),
(1, "Piment", 27),
(1, "Poireau", 61),
(1, "Pois", 81),
(1, "Poivron", 27),
(1, "Pomme de Terre", 77),
(1, "Radis", 16),
(1, "Roquette", 25),
(1, "Rutabaga", 38),
(1, "Tomate", 18),
(1, "Tomate Cerise", 100),
(1, "Wasabi", 109),
(1, "Echalotte", 72),
(1, "Epinards", 23),
(1, "Babybel", 333),
(1, "Beaufort", 401),
(1, "Bleu d'Auvergne", 341),
(1, "Boursin", 409),
(1, "Brie", 334),
(1, "Brillat-Savarin", 384),
(1, "Camembert", 300),
(1, "Cantal", 371),
(1, "Chaource", 292),
(1, "Cheddar", 403),
(1, "Chester", 387),
(1, "Comté", 417),
(1, "Cottage Cheese", 98),
(1, "Coulommiers", 287),
(1, "Crotin de Chèvre", 367),
(1, "Edam", 357),
(1, "Emmental", 357),
(1, "Epoisses", 290),
(1, "Feta", 264),
(1, "Fondue au fromage", 228),
(1, "Fromage de Brebis", 364),
(1, "Fromage de Chèvre", 364),
(1, "Fromage Italien", 397),
(1, "Fromage Néerlandais", 393),
(1, "Fromage Suisse", 380),
(1, "Fromage à Pâte Molle", 268),
(1, "Fromage à Raclette", 357),
(1, "Gorgonzola", 350),
(1, "Gouda", 356),
(1, "Gruyère Suisse", 413),
(1, "Maasdam", 344),
(1, "Manchego", 320),
(1, "Maroilles", 341),
(1, "Mimolette Vielle", 421),
(1, "Mont d'Or", 350),
(1, "Mozzarella", 280),
(1, "Munster", 368),
(1, "Ossau-iraty", 355),
(1, "Parmesan", 431),
(1, "Pecorino", 387),
(1, "Provolone", 351),
(1, "Reblochon", 354),
(1, "Ricotta", 174),
(1, "Rocamadour", 283),
(1, "Roquefort", 369),
(1, "Saint-Morêt", 203),
(1, "Saint-Nectaire", 341),
(1, "Stilton", 393),
(1, "Tomme des Pyrénées", 283),
(1, "Vacherin", 288),
(1, "Leerdammer", 284),
(1, "Kiri", 310),
(1, "Mascarpone", 450),
(1, "Vache Qui Rit", 275),
(1, "Crème Fraiche", 393),
(1, "Fromage Blanc", 98),
(1, "Kéfir", 55),
(1, "Lait", 61),
(1, "Lait Concentré Non Sucré", 134),
(1, "Lait Concentré Sucré", 321),
(1, "Lait de Chèvre", 69),
(1, "Lait de Coco", 230),
(1, "Lait de Riz", 49),
(1, "Lait de Soja", 45),
(1, "Lait Demi-Ecrémé", 50),
(1, "Lait d'Amande", 17),
(1, "Lait en Poudre", 496),
(1, "Lait Entier", 61),
(1, "Lassi", 75),
(1, "Lait Sans Lactose", 52),
(1, "Lait Ecrémé", 35),
(1, "Petits-Suisses", 135),
(1, "Semoule au Lait", 67),
(1, "Tzatziki", 117),
(1, "Yaourt", 61),
(1, "Activia", 74),
(1, "Activia au Citron", 100),
(1, "Activia à la Fraise", 97),
(1, "Ayran", 42),
(1, "Yaourt", 61),
(1, "Yaourt Allégé", 63),
(1, "Yaourt Grec", 53),
(1, "Yaourt Nature", 66),
(1, "Bavette", 194),
(1, "Boeuf", 171),
(1, "Boeuf Haché", 241),
(1, "Châteaubriand", 121),
(1, "Collier de Boeuf", 213),
(1, "Côtes de Boeuf", 196),
(1, "Côtes de Veau", 237),
(1, "Entrecôte", 217),
(1, "Faux-Filet", 117),
(1, "Filet de Boeuf", 189),
(1, "Filet de Veau", 217),
(1, "Filet Mignon", 207),
(1, "Flanchet", 231),
(1, "Goulasch de Boeuf", 123),
(1, "Gîte", 158),
(1, "Hampe", 205),
(1, "Jarret de Veau", 177),
(1, "Noix de Veau", 228),
(1, "Onglet", 171),
(1, "Paleron", 137),
(1, "Poitrine de Boeuf", 271),
(1, "Poitrine de Veau", 282),
(1, "Quasi de Veau", 171),
(1, "Queue de Boeuf", 196),
(1, "Ragoût de boeuf", 191),
(1, "Ris de Boeuf", 319),
(1, "Rumsteck", 171),
(1, "Rôti de Veau", 175),
(1, "Steak Haché", 247),
(1, "Tendron", 210),
(1, "Tournedos", 188),
(1, "Tripes de Boeuf", 94),
(1, "Viande Haché", 250),
(1, "Emincé de Veau", 143),
(1, "Epaule de Veau", 183),
(1, "Baloney", 247),
(1, "Blanc de Dinde", 104),
(1, "Bresaola", 178),
(1, "Chorizo", 455),
(1, "Coppa", 110),
(1, "Corned Beef", 251),
(1, "Filet de Poulet", 79),
(1, "Jambon", 145),
(1, "Jambon Blanc", 343),
(1, "Jambon de Dinde", 126),
(1, "Jambon de Dinde Cuit au Four", 104),
(1, "Jambon de Dinde Fumé", 104),
(1, "Jambon de Parme", 345),
(1, "Jambon Fumé", 375),
(1, "Jambon Serrano", 300),
(1, "Pastrami", 133),
(1, "Prosciutto", 195),
(1, "Rosbif", 267),
(1, "Rôti de Porc", 247),
(1, "Salami", 336),
(1, "Salami de Dinde", 152),
(1, "Saucisse Sèche", 390),
(1, "Saucisson à l'Ail", 286),
(1, "Bacon", 407),
(1, "Carré", 396),
(1, "Carré de Porc", 241),
(1, "Côtelette de Porc", 123),
(1, "Côtes de Porc", 292),
(1, "Echine", 302),
(1, "Escalope de Porc", 131),
(1, "Estomac de Porc", 250),
(1, "Farce", 193),
(1, "Filet Mignon", 125),
(1, "Hachis de Porc", 263),
(1, "Jambe de Porc", 201),
(1, "Jambon de Parme", 261),
(1, "Jambon Serrano", 339),
(1, "Jambonneau", 144),
(1, "Jarret de Porc", 172),
(1, "Lard", 407),
(1, "Lardon", 338),
(1, "Longe de Porc", 143),
(1, "Omoplate de Porc", 232),
(1, "Oreille de Porc", 166),
(1, "Ragoût de Porc", 73),
(1, "Rôti de Porc", 194),
(1, "Saucisse de Porc", 339),
(1, "Travers de Porc", 216),
(1, "Tripes", 233),
(1, "Epaule de Porc", 269),
(1, 'Andouille', 232),
(1, 'Andouillette', 267),
(1, 'Baloney', 247),
(1, 'Blanc de poulet', 79),
(1, 'Bockwurst', 301),
(1, 'Boudin blanc', 270),
(1, 'Boudin noir', 379),
(1, 'Bratwurst', 297),
(1, 'Cervelas', 320),
(1, 'Chipolata', 341),
(1, 'Chorizo', 455),
(1, 'Corned beef', 153),
(1, 'Diot', 342),
(1, 'Foie gras', 479),
(1, 'Gendarme', 352),
(1, 'Hot Dog', 278),
(1, 'Kielbasa', 309),
(1, 'Knack', 307),
(1, 'Lard', 407),
(1, 'Merguez', 279),
(1, 'Mettwurst', 310),
(1, 'Mortadelle', 311),
(1, 'Mousse de foie', 280),
(1, 'Pastrami', 133),
(1, 'Porc', 247),
(1, 'Pâté de canard', 436),
(1, 'Pâté de foie', 326),
(1, 'Pâté de foie', 319),
(1, 'Pâté en croûte', 270),
(1, 'Rillettes', 357),
(1, 'Salade de poulet', 81),
(1, 'Saucisse', 230),
(1, 'Saucisse blanche', 313),
(1, 'Saucisse de Francfort', 305),
(1, 'Saucisse de Morteaux', 320),
(1, 'Saucisse de Strasbourg', 281),
(1, 'Saucisse de Toulouse', 254),
(1, 'Saucisse fumée', 301),
(1, 'Saucisse italienne', 149),
(1, 'Terrine de campagne', 284),
(1, 'Viande de poulet', 79),
(1, "Agneau", 202),
(1, "Alligator", 104),
(1, "Antilope", 150),
(1, "Autruche", 175),
(1, "Bison", 171),
(1, "Buffle", 131),
(1, "Canard colvert", 211),
(1, "Caribou", 159),
(1, "Castor", 212),
(1, "Cerf", 119),
(1, "Chevreuil", 164),
(1, "Faisan", 239),
(1, "Faux-filet de bison", 171),
(1, "Faux-filet de chevreuil", 158),
(1, "Lapin", 197),
(1, "Lièvre", 124),
(1, "Mouton", 234),
(1, "Oie", 238),
(1, "Ours", 259),
(1, "Pavé de biche", 116),
(1, "Perdrix", 222),
(1, "Raton laveur", 255),
(1, "Renne", 200),
(1, "Sanglier", 160),
(1, "Écureuil", 173),
(1, "Élan", 134),
(1, "Aile de poulet", 266),
(1, "Ailes de dinde", 221),
(1, "Autruche", 145),
(1, "Blanc de dinde", 135),
(1, "Blanc de poulet", 172),
(1, "Caille", 227),
(1, "Canard", 337),
(1, "Canard sauvage", 211),
(1, "Chapon", 229),
(1, "Cuisse de faisan", 239),
(1, "Cuisses de dinde", 208),
(1, "Cuisses de poulet", 185),
(1, "Dinde", 189),
(1, "Escalope de dinde", 189),
(1, "Faisan", 239),
(1, "Filet de caille", 123),
(1, "Filet de faisan", 133),
(1, "Gésiers de poulet", 154),
(1, "Magret de canard", 201),
(1, "Oie", 305),
(1, "Pigeon", 142),
(1, "Pilon de dinde", 208),
(1, "Pintade", 158),
(1, "Poularde", 200),
(1, "Poulet", 219),
(1, "Cheerios", 347),
(1, "Cinnamon Toast Crunch", 433),
(1, "Rice Krispies au chocolat", 386),
(1, "Coco Pops", 400),
(1, "Cookie Crisp", 400),
(1, "Corn flakes", 357),
(1, "Corn pops", 377),
(1, "Crème de blé", 364),
(1, "Crunchy Nut", 600),
(1, "Crunchy Nut Cornflakes", 600),
(1, "Froot Loops", 379),
(1, "Frosted Mini-Wheats", 353),
(1, "Frosties", 367),
(1, "Smacks", 370),
(1, "Corn Flakes de Kelloggs", 357),
(1, "Kix", 367),
(1, "Lucky Charms", 406),
(1, "Mini-Wheats", 353),
(1, "Muesli", 336),
(1, "Multigrain Cheerios", 380),
(1, "Flocons d’avoine", 375),
(1, "Riz soufflé", 383),
(1, "Blé soufflé", 367),
(1, "Rice Krispies", 394),
(1, "Shreddies", 351),
(1, "Special K", 377),
(1, "Special K Feuilles de chocolat noir", 388),
(1, "Special K Protein Plus", 375),
(1, "Special K aux fruits rouges", 345),
(1, "Trix", 400),
(1, "Weetabix", 358),
(1, "Chocos", 380),
(1, "Coco Pops", 376),
(1, "Miel Pops", 381),
(1, "Crunch", 413),
(1, "Céréales Lion", 419),
(1, "Bavette", 356),
(1, "Boulettes de pâte", 99),
(1, "Cannellonis", 146),
(1, "Capellinis", 353),
(1, "Cappellettis", 164),
(1, "Conchiglie", 353),
(1, "Coquillettes", 354),
(1, "Crozets", 362),
(1, "Dampfnudel", 274),
(1, "Dangmyeon", 192),
(1, "Farfalles", 358),
(1, "Fettuccine", 353),
(1, "Feuilles de lasagnes", 271),
(1, "Fusillis", 352),
(1, "Linguine", 357),
(1, "Macaronis", 370),
(1, "Mafaldine", 358),
(1, "Mostaccioli", 184),
(1, "Nouilles aux oeufs", 384),
(1, "Nouilles de soja", 216),
(1, "Nouilles Shirataki", 18),
(1, "Orecchiette", 370),
(1, "Papardelle", 284),
(1, "Penne", 351),
(1, "Penne Rigate", 370),
(1, "Pierogi", 200),
(1, "Pâtes alphabet", 375),
(1, "Pâtes au blé complet", 347),
(1, "Raviolis", 77),
(1, "Rigatonis", 353),
(1, "Risoni", 357),
(1, "Semoule de blé dur", 397),
(1, "Spaetzle", 368),
(1, "Spaghettis", 370),
(1, "Spaghettis au blé complet", 351),
(1, "Spirellis", 367),
(1, "Tagliatelles", 370),
(1, "Tortellinis", 291),
(1, "Tortellinis au fromage", 291),
(1, "Tortellinis aux épinards", 314),
(1, "Vermicelles", 368),
(1, 'Amarante', 371),
(1, 'Amidon de blé', 351),
(1, 'Biscotte', 410),
(1, 'Blé de Khorasan', 337),
(1, 'Blé précuit (Ebly)', 127),
(1, 'Bouillie de millet', 46),
(1, 'Chips de crevettes', 527),
(1, 'Cracker', 502),
(1, 'Farine de blé complet', 339),
(1, 'Farine de millet', 372),
(1, 'Flocons d’avoine complet', 375),
(1, 'Germe de blé', 382),
(1, 'Gluten de blé', 325),
(1, 'Graines de lin', 534),
(1, 'Graines de tournesol', 584),
(1, 'Gressin', 408),
(1, 'Maïzena', 381),
(1, 'Millet', 378),
(1, 'Orge', 354),
(1, 'Orge perlée', 352),
(1, 'Petit beurre', 502),
(1, 'Polenta', 366),
(1, 'Quinoa', 368),
(1, 'Riz complet', 388),
(1, 'Sagou', 354),
(1, 'Sarrasin', 343),
(1, 'Semoule de blé', 360),
(1, 'Semoule de blé dur', 397),
(1, 'Semoule de couscous', 376),
(1, 'Semoule de maïs', 362),
(1, 'Semoule d’épeautre', 360),
(1, 'Son de blé', 216),
(1, 'Son de seigle', 281),
(1, 'Son d’avoine', 246),
(1, 'Son d’épeautre', 177),
(1, 'Stick salé', 380),
(1, 'Tortilla', 237),
(1, 'Tortillas chips', 499),
(1, 'Épeautre', 338),
(4, 'Beurre', 720),
(4, 'Beurre clarifié', 120),
(4, 'Huile de carthame', 857),
(4, 'Huile de coco', 857),
(4, 'Huile de colza', 884),
(4, 'Huile de coton', 882),
(4, 'Huile de foie de morue', 1000),
(4, 'Huile de hareng', 902),
(4, 'Huile de lin', 884),
(4, 'Huile de macadamia', 819),
(4, 'Huile de maïs', 800),
(4, 'Huile de moutarde', 884),
(4, 'Huile de noix', 889),
(4, 'Huile de palme', 882),
(4, 'Huile de pépins de raisin', 884),
(4, 'Huile de sardine', 902),
(4, 'Huile de soja', 889),
(4, 'Huile de sésame', 884),
(4, 'Huile de tournesol', 884),
(4, "Huile d'amande", 882),
(4, "Huile d'arachide", 857),
(4, "Huile d'argan", 899),
(4, "Huile d'avocat", 857),
(4, "Huile d'olive", 884),
(4, 'Margarine', 717),
(4, 'Saindoux', 902),
(4, 'Huile végétale', 800),
(4, "Huile d'avoine", 857),
(1, 'Alose', 252),
(1, 'Anchois', 131),
(1, 'Anguille', 236),
(1, 'Baliste', 93),
(1, 'Brochet', 113),
(1, 'Brème', 135),
(1, 'Calmar', 92),
(1, 'Carpe', 162),
(1, 'Caviar', 264),
(1, 'Chanos', 190),
(1, 'Coquilles Saint-Jacques', 111),
(1, 'Crabe', 91),
(1, 'Encornet', 175),
(1, 'Espadon', 172),
(1, 'Esturgeon', 135),
(1, 'Flet', 86),
(1, 'Flétan', 111),
(1, 'Hareng', 203),
(1, 'Hareng mariné', 262),
(1, 'Hareng saur', 217),
(1, 'Hoki', 121),
(1, 'Homard', 89),
(1, 'Huitres', 65),
(1, 'Langouste', 82),
(1, 'Langoustine', 84),
(1, 'Lieu jaune', 111),
(1, 'Lingue blanche', 109),
(1, 'Lotte', 97),
(1, 'Loup de mer', 124),
(1, 'Maquereau', 262),
(1, 'Merlan', 116),
(1, 'Merlu', 71),
(1, 'Morue', 105),
(1, 'Moule', 172),
(1, 'Moules', 69),
(1, 'Mulet', 150),
(1, 'Mérou', 118),
(1, 'Palourde', 148),
(1, 'Plie', 91),
(1, 'Poisson blanc', 172),
(1, 'Poisson pané', 290),
(1, 'Poulpe', 164),
(1, 'Requin', 130),
(1, 'Rollmops', 171),
(1, 'Salade de thon', 187),
(1, 'Sandre', 84),
(1, 'Sardines', 208),
(1, 'Saumon', 206),
(1, 'Sole', 86),
(1, 'Stromatée à fossettes', 187),
(1, 'Sushi', 150),
(1, 'Sébaste', 94),
(1, 'Tassergal', 159),
(1, 'Thazard noir', 167),
(1, 'Thon', 132),
(1, 'Truite', 190),
(1, 'Turbot', 122),
(1, 'Vivaneau', 128),
(1, 'Écrevisse', 87),
(1, 'Églefin', 90),
(1, 'Éperlan', 124),
(6, 'Ail', 149),
(6, 'Ail en poudre', 331),
(6, 'Aneth', 43),
(6, 'Anis', 337),
(6, 'Basilic', 233),
(6, 'Bette', 19),
(6, 'Bouillon', 100),
(6, 'Canelle', 247),
(6, 'Carvi', 333),
(6, 'Ciboulette', 30),
(6, 'Clous de girofle', 274),
(6, 'Condiment liquide Maggi', 104),
(6, 'Coriandre', 23),
(6, 'Cresson', 32),
(6, 'Cumin', 375),
(6, 'Curcuma', 354),
(6, 'Curry', 325),
(6, 'Câpres', 23),
(6, 'Estragon', 295),
(6, 'Extrait de vanille', 288),
(6, "Extrait d'amande", 374),
(6, 'Fenouil', 31),
(6, 'Feuilles de chicorée', 23),
(6, 'Feuilles de vigne', 93),
(6, 'Genièvre', 45),
(6, 'Gingembre', 80),
(6, 'Gingembre confit', 335),
(6, 'Gombo', 33),
(6, 'Gousse de vanille', 250),
(6, 'Graines de fenouil', 345),
(6, 'Graines de moutarde', 508),
(6, 'Graines de pavot', 525),
(6, 'Graines de sésame', 573),
(6, 'Graines de sésame noir', 573),
(6, "Graines d'anis", 337),
(6, 'Manioc', 160),
(6, 'Marjolaine', 271),
(6, 'Menthe', 70),
(6, 'Noix de muscade', 525),
(6, 'Oignon', 40),
(6, 'Origan', 265),
(6, 'Persil', 36),
(6, 'Piment', 282),
(6, 'Piment jalapeño', 124),
(6, 'Pissenlit', 45),
(6, 'Poivre', 251),
(6, 'Poivre blanc', 296),
(6, 'Poivre de cayenne', 318),
(6, 'Poivre noir', 251),
(6, 'Poivron rouge', 251),
(6, 'Poudre de chili', 282),
(6, "Poudre d'oignon", 124),
(6, 'Racine de chicorée', 72),
(6, 'Raifort', 48),
(6, 'Romarin', 131),
(6, 'Roquette', 25),
(6, 'Réglisse', 124),
(6, 'Safran', 310),
(6, 'Sauge', 315),
(6, 'Sel', 0),
(6, 'Stevia', 0),
(6, 'Tamarin', 239),
(6, 'Taro', 112),
(6, 'Thym', 276),
(6, 'Vinaigre', 18),
(6, 'Vinaigre balsamique', 88),
(6, 'Vinaigre de cidre', 21),
(6, 'Vinaigre de vin rouge', 19),
(6, "Zeste d'orange", 97),
(4, "Huile de truffe", 800),
(1, "Truffe", 92),
(1, "Concentré de tomates", 92),
(1, "Sucre blanc", 398),
(4, "Sauce soja sucré", 263),
(4, "Sauce soja salé", 78),
(1, "Céléri", 16),
(1, "Nouilles chinoises", 165),
(6, "Oeuf", 150),
(4, "Sauce poisson", 45),
(1, "Bouillon de volaille", 9),
(4, "Miel", 433),
(1, "Pâte feuilletée", 505),
(1, "Branche de céléri", 22),
(1, "Riz rond", 348),
(1, "Pousses de soja", 44),
(1, "Epinards", 23);

INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("FACILE", true, "Spaghetti bolognaise", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(1, "ITALIEN");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(1, 1, "Découpe légumes", "Emincez les légumes", 10),
(1, 2, "Cuisson oignons", "Faites chauffer 1 c. à soupe d'huile d'olive dans une casserole et faites-y revenir l'oignon et l'ail émincés, tout en remuant, jusqu'à ce qu'ils deviennent transparents.", 5),
(1, 3, "Cuisson légumes", "Ajoutez les autres légumes et laissez mijoter pendant ± 4 min. à feu doux et à découvert.", 4),
(1, 4, "Cuisson hachis", "Faites chauffer 1 c. à soupe d'huile d'olive dans une poêle et faites-y cuire le hachis jusqu'à obtention d'une viande granuleuse (émiettez-le à l'aide d'une fourchette).", 5),
(1, 5, "Ajouter tout", "Ajoutez le hachis, le concentré de tomates ainsi que les tomates pelées aux légumes et remuez bien. Assaisonnez de sel, de poivre et de poivre de Cayenne, et d'autres épices selon votre goût.", 6),
(1, 6, "Mijoter", "Couvrez et laissez mijoter pendant ± 1 h à feu doux. Entre-temps, ajoutez-y aussi un soupçon de bouillon de poule ou de vin rouge.", 60),
(1, 7, "Servez", "Servez la sauce avec des spaghettis.", 2);

INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(1, 250, 300),
(1, 184, 300),
(1, 94, 800),
(1, 635, 70),
(1, 564, 10),
(1, 79, 80),
(1, 498, 15),
(1, 53, 250),
(1, 69, 60),
(1, 89, 150),
(1, 54, 150);


INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("FACILE", true, "Curry japonais", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(2, "JAPONAIS");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(2, 1, "Oignons", "Couper les oignons en tranche. Faites les cuir dans une poêle", 5),
(2, 2, "Carottes", "Couper les carotten et la viande en morceaux, Faites cuire les carottes dans une casserole d'eau", 5),
(2, 3, "Ajout viande", "Ajoutez la viande avec les oignons cuits avec une cuillère à soupe de miel et une peu d'épices à poulet", 8),
(2, 4, "Eau", "Dès que le poulet est saisi, ajoutez 750 ml d'eau", 1),
(2, 5, "Bouillon de poule", "Ajoutez le cube de bouillon de poule", 1),
(2, 6, "Roux de curry", "Pour la préparation du roux de curry, mélangez 30 gr de beurre, 30 gr de farine, un peu de ketchup et une càs de curry. Ajoutez la ensuite dans la casserole", 5),
(2, 7, "Epaissir le curry", "Ajoutez du pain dans le curry en morceaux", 2),
(2, 8, "Cuisson riz", "Pendant que le curry mijote, cuisez le riz", 20);


INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(2, 79, 200), #oignon
(2, 53, 400), #carotte
(2, 459, 400), #riz
(2, 270, 500), #blanc de poulet
(2, 644, 15), #miel
(2, 475, 30), #beurre
(2, 444, 30), #farine
(2, 580, 3), #curry
(2, 643, 15); #bouillon de volaille


INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("FACILE", true, "Tarte courgette, tomates, chèvre", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(3, "PEU CALORIQUE");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(3, 1, "Préparation", "Préchauffez le four à 180 degrée Celsius. Epeluchez les courgettes et coupez-les en rondelles. Fqites-les cuire à la vapeur. Piquez la pâte et badigeonnez-la de moutarde", 25),
(3, 2, "Préparation 2", "Coupez la tomate en rondelles. La demi bûche de chèvre en morceaux et le filet de poulet en tout petits morceaux. Quand les courgettes sont cuites, disposez-les sur la pâte. Ajoutez les rondelles de tomate, puis les petits dés de poulet", 20),
(3, 3, "Finition et cuisson", "Disposez les morceaux de chèvre, le gruyère râpé allégé, salez et poivrez. Enfournez 30 min", 35);


INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(3, 645, 200), #pate feuilleté
(3, 68, 400), #courgettes
(3, 223, 150), #poulet
(3, 94, 75), #tomate
(3, 120, 120), #chèvre
(3, 128, 100), #gruyère
(3, 76, 30); #moutarde

INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("DIFFICILE", true, "Bibimbap", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(4, "COREEN");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(4, 1, "Préparations Marinade", "Dans un bol mélangez 4càs de sauce soja, 2càs de miel, 2càs d'huile de sésame, 2càs de vinaigre de riz, 4 gousses d'ail écrasé, 2 pincées de poivre", 5),
(4, 2, "Marinade viande", "Dans un récipient, mélangez les viandes hachées avec tous les ingrédients de la marinade, puis réservez au frais.", 5),
(4, 3, "Préparation légumes", "Coupez, épépinez et taillez le concombre en lamelles. Pelez et coupez en petits bâtonnets la carotte. Pelez et émincez finement l'oignon. Rincez les épinards et les pousses de soja. pelez et hachez finement les gousses d'ail", 15),
(4, 4, "Cuisson riz", "Dans une casserole remplie d'eau bouillante, faites cuire le riz selon les indications du paquet.", 20),
(4, 5, "Préparation sauce", "mélangez 2càs d'huile de sésame, 2càc de Graine de sésame, 6càs de pâte de piment, 2càs de miel liquide ainsi que 4càs d'eau", 1),
(4, 6, "Cuisson légumes", "Pendant la cuisson du riz, faites revenir les légumes (sauf l'ail et l'oignon) les uns après les autres dans des poêles bien chaudes avec un filet d'huile de sésame. Ajoutez à chacun un peu d'ail et d'oignon ainsi qu'une pincée de sucre blanc. Réservez hors du feu", 10),
(4, 7, "Cuisson viande", "Dans une autre poêle avec un filet d'huile, faites revenir les viandes avec la marinade. Retirez et réservez hors du feu", 5),
(4, 8, "Mise en place", "Quand le riz est cuit, égouttez et répartissez-le dans les bols. Versez dans chaque bol 2 cuillères à soupe de sauce et recouvrez d'un jaune d'oeuf entier, au centre, puis répartissez tout autour le mélange de viande et les légumes poêlés, sans les mélanger", 5),
(4, 9, "Finitions", "Saupoudrez de graines de sésame et servez sans attendre.", 0);


INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(4, 647, 400), #riz rond
(4, 184, 200), #haché de boeuf
(4, 250, 200), #haché de porc
(4, 53, 230), #carottes
(4, 65, 150), #concombres
(4, 649, 300), #Epinards
(4, 648, 200), #Pousses de soja
(4, 603, 150), #Oignons
(4, 564, 70), #Ail
(4, 641, 4), #oeufs
(4, 636, 25), #sucre blanc
(4, 492, 30), #huile de sésame
(4, 596, 15); #graine de sésame


INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("MOYEN", TRUE, "Nouilles chinoises", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(5, "CHINOIS");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(5, 1, "Découpe légumes", "Laver et couper les légumes : Les courgettes en demi-rondelles, les poivrons en bâtonnets, le céleri en petits tronçons. Émincer les champignons de Paris. Peler et couper finement l'ail.", 10),
(5, 2, "Découpe poulet", "Mettre de l'eau à bouillir pour les nouilles. Pendant ce temps, couper le poulet en lanières.", 5),
(5, 3, "Cuisson légumes", "Dans un wok, ou à défaut dans une sauteuse, faire revenir dans 2 cuillères à soupe d'huile (neutre comme celle de pépin de raisin par exemple), tous les légumes et les champignons. Remuer régulièrement. Lorsque les légumes sont bien tendres, ajouter les petits sachets d'épices qui sont fournis dans les paquets de nouilles (on ne se sert pas de l'huile qui est elle aussi fournie avec).", 5),
(5, 4, "Cuisson poulet", "Faites cuire le poulet dans un poêle à part avec un peu d'huile. Lorsqu'il est bien doré, déglacer avec la sauce soja. Faire caraméliser légèrement sur feu doux. Puis ajouter le poulet dans le wok avec les légumes. Continuer de cuire à feu doux.", 5),
(5, 5, "Cuisson nouiles", "Lorsque l'eau boue, plonger les plaques de nouilles pendant le temps indiqué sur l'emballage, en général 3 minutes suffisent. Égoutter les nouilles.", 3),
(5, 6, "Ajouter tout", "Une fois égouttée, vous pouvez ajouter l'ensemble dans le wok et servir !", 1);

INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(5, 640, 400),
(5, 223, 350),
(5, 68, 200),
(5, 613, 50),
(5, 639, 80),
(5, 54, 40),
(5, 564, 40),
(5, 638, 60);

INSERT INTO Recipe (complexity, isVisible, title, lastUpdate) VALUES
("MOYEN", true, "Kimchi", NOW());

INSERT INTO Taglink (recipe, tag) VALUES
(6, "COREEN");

INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES
(6, 1, "Préparation du chou", "Coupez le chou en lamelles dans le sens de la longueur et mettez-le dans un saladier rempli d’eau tiède salée. Saupoudrez le chou de gros sel (à mettre entre chaque feuille). Laissez reposer et dégorger pendant une nuit. Le lendemain, rincez les lamelles de chou à l’eau et égouttez-les.", 10),
(6, 2, "Découpe légumes", "Coupez le navet et les poireaux en fines lamelles d’environ 5 cm. Mélangez les ingrédients pour faire la pâte d’épices : gingembre,piment rouge, ail,sucre et sauce de poisson. Il faut tout éplucher ou râper afin de faire la pâte.", 15),
(6, 3, "Ajouter le tout", "Étalez cette pâte entre les feuilles de chou. Déposez les morceaux de chou dans un grand récipient. Saupoudrez avec un peu de sésame grillé. Placez un couvercle ou une assiette sur le récipient, ou fermez-le hermétiquement. Il faut maintenant attendre 4 à 5 jours pour que le kimchi soit prêt. ", 5);


INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES
(6, 87, 120),
(6, 77, 100),
(6, 564, 40),
(6, 62, 15),
(6, 589, 130),
(6, 596, 15),
(6, 636, 20),
(6, 623, 5);