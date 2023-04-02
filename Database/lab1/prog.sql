DROP TYPE IF EXISTS entity_tables CASCADE;
CREATE TYPE entity_tables AS ENUM(
'tents',
'bases',
'places',
'radiograms',
'people',
'materials'
);

DROP TABLE IF EXISTS Item CASCADE;
CREATE TABLE Item(
id SERIAL PRIMARY KEY,
types entity_tables NOT NULL
);

DROP TABLE IF EXISTS Action CASCADE;
CREATE TABLE Action(
id serial PRIMARY KEY,
name text
);

DROP TABLE IF EXISTS Item_to_Action CASCADE;
CREATE TABLE Item_to_Action(
id serial PRIMARY KEY,
action_id INTEGER REFERENCES Action (id),
item_id INTEGER REFERENCES Item (id)
);

DROP TABLE IF EXISTS Place CASCADE;
CREATE TABLE Place(
id serial PRIMARY KEY,
name text,
item_id INTEGER REFERENCES Item (id)
);

DROP TABLE IF EXISTS Base CASCADE;
CREATE TABLE Base(
id serial PRIMARY KEY,
name varchar(100),
item_id INTEGER REFERENCES Item (id),
place_id INTEGER REFERENCES Place (id),
is_opened BOOLEAN
);

DROP TABLE IF EXISTS Tent CASCADE;
CREATE TABLE Tent(
id serial PRIMARY KEY,
item_id INTEGER REFERENCES Item (id),
base_id INTEGER REFERENCES Place (id),
is_reinforced BOOLEAN
);

DROP TABLE IF EXISTS Material CASCADE;
CREATE TABLE Material(
id serial PRIMARY KEY,
name varchar(50)
);

DROP TABLE IF EXISTS Material_to_Tent CASCADE;
CREATE TABLE Material_to_Tent(
id serial PRIMARY KEY,
material_id INTEGER REFERENCES Material (id),
tent_id INTEGER REFERENCES Tent (id)
);

DROP TABLE IF EXISTS Person CASCADE;
CREATE TABLE Person(
id serial PRIMARY KEY,
name varchar(20),
base_id INTEGER REFERENCES Base (id)
);

DROP TABLE IF EXISTS Person_to_Place CASCADE;
CREATE TABLE Person_to_Place(
id serial PRIMARY KEY,
place_id INTEGER REFERENCES Place (id),
person_id INTEGER REFERENCES Person (id)
);

DROP TABLE IF EXISTS Person_to_Action CASCADE;
CREATE TABLE Person_to_Action(
id serial PRIMARY KEY,
action_id INTEGER REFERENCES Action (id),
person_id INTEGER REFERENCES Person (id)
);

DROP TABLE IF EXISTS Radiogram CASCADE;
CREATE TABLE Radiogram(
id serial PRIMARY KEY,
message text,
getter_id INTEGER REFERENCES Person (id),
sender_id INTEGER REFERENCES Person (id),
item_id INTEGER REFERENCES Item (id)
);

INSERT INTO Item(types) VALUES ('places');
INSERT INTO Item(types) VALUES ('places');
INSERT INTO Item(types) VALUES ('places');
INSERT INTO Place(name, item_id)
VALUES	('Антарктика', 1),
		('Россия', 2),
		('Гора', 3);

INSERT INTO Item(types) VALUES ('bases');
INSERT INTO Item(types) VALUES ('bases');
INSERT INTO Base(name, item_id, place_id, is_opened)
VALUES	('Аркхем', 4, 1, FALSE),
		('ИТМО', 5, 3, TRUE);

INSERT INTO Person(name, base_id)
VALUES	('Пэбоди', 1),
		('Андрей', 2),
		('Алекс', 1);

INSERT INTO Person_to_Place(place_id, person_id)
VALUES	(1, 1),
		(1, 3);

INSERT INTO Item(types) VALUES ('tents');
INSERT INTO Item(types) VALUES ('tents');
INSERT INTO Tent(is_reinforced, item_id, base_id)
VALUES	(FALSE, 6, 1),
		(TRUE, 7, 2);

INSERT INTO Material(name)
VALUES	('Снег'),
		('Древесина'),
		('Камень');

INSERT INTO Material_to_Tent(material_id, tent_id)
VALUES	(1, 2);

INSERT INTO Item(types) VALUES ('radiograms');
INSERT INTO Radiogram(message, sender_id, getter_id, item_id)
VALUES	('Привет', 1, 2, 8);

INSERT INTO Action(name)
VALUES	('Послать'),
		('Закрыть'),
		('Укрепить');
		
INSERT INTO Item_to_Action(action_id, item_id)
VALUES	(1, 8);

INSERT INTO Person_to_Action(action_id, person_id)
VALUES	(1, 1);