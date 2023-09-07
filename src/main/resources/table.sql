CREATE TABLE table_users (id SERIAL8 NOT NULL, status BOOLEAN DEFAULT FALSE, name VARCHAR(255) UNIQUE );

INSERT INTO table_users (name) VALUES ('Mik'), ('Nik'), ('Bob'),('Sam');