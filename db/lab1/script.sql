BEGIN;

CREATE TYPE entity_tables AS ENUM
    ('place', 'tent', 'radiogram', 'base');

CREATE TABLE IF NOT EXISTS "Item"
(
    type entity_tables NOT NULL,
    id bigint PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS "Place"
(
    id bigint,
    coordinates numeric(10, 7)[],
    name text,
    item_id bigint NOT NULL REFERENCES Item,
	PRIMARY KEY (id, coordinates)
);

CREATE TABLE IF NOT EXISTS "Base"
(
    id bigint PRIMARY KEY,
    is_opened boolean,
    name text,
    location numeric(10, 7)[] REFERENCES Place(coordinates),
    item_id bigint NOT NULL REFERENCES Item
);

CREATE TABLE IF NOT EXISTS Person
(
    id bigint PRIMARY KEY,
    name text,
    base_id bigint REFERENCES Base(id),
    place_id bigint NOT NULL REFERENCES Place(id)
);

CREATE TABLE IF NOT EXISTS Action
(
    id bigint PRIMARY KEY,
    name text NOT NULL,
    obj_id bigint REFERENCES Item,
    subj_id bigint REFERENCES Person(id)
);

CREATE TABLE IF NOT EXISTS Tent
(
    id bigint PRIMARY KEY,
    base_id bigint REFERENCES Base(id),
    is_reinforced boolean
);

CREATE TABLE IF NOT EXISTS Material
(
    id bigint PRIMARY KEY,
    name text NOT NULL,
    tent_id bigint REFERENCES Tent(id)
);

CREATE TABLE IF NOT EXISTS Radiogram
(
    id bigint PRIMARY KEY,
    sender_id bigint NOT NULL REFERENCES Person(id),
    getter_id bigint REFERENCES Person(id)
);

END;