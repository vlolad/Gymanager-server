DROP FUNCTION IF EXISTS generateid CASCADE;
DROP TABLE IF EXISTS _users_roles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS main_id_sequence;

BEGIN;

-- Author: https://github.com/PavelProjects
CREATE SEQUENCE main_id_sequence;

create function generateid()
    returns char(8) as
'
    declare
-- add prefix for server identify
        str text := ''0123456789abcdefghijklmnopqrstuvwxyz'';
        val bigint;
        id_ text;
        mod int;
    begin
        val := nextval(''main_id_sequence'');
        id_ := '''';
        while (length(id_) < 8)
            loop
                mod = val % 36;
                id_ := substring(str, mod + 1, 1) || id_;
                val = val / 36;
            end loop;
        return id_;
        return ''null'';
    end;
'
    language plpgsql;

CREATE TABLE users
(
    id       char(8) PRIMARY KEY DEFAULT generateid(),
    name     varchar(32)  NOT NULL,
    login    varchar(32)  NOT NULL UNIQUE,
    email    varchar(128) NOT NULL UNIQUE,
    password varchar(128) NOT NULL
);

CREATE TABLE roles
(
    id   char(8) PRIMARY KEY DEFAULT generateid(),
    name varchar(32) NOT NULL
);

CREATE TABLE users_roles
(
    user_id char(8) REFERENCES users (id) NOT NULL,
    role_id char(8) REFERENCES roles (id) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);

CREATE TABLE clients
(
    id char(8) PRIMARY KEY DEFAULT generateid(),
    name varchar(64) NOT NULL,
    description text,
    next_workout timestamp,
    trainer_id char(8) REFERENCES users (id) NOT NULL
);
CREATE UNIQUE INDEX clients_name on clients (name);

CREATE TABLE workouts
(
    id char(8) PRIMARY KEY DEFAULT generateid(),
    client_id char(8) NOT NULL REFERENCES clients (id),
    date timestamp,
    description text
);
CREATE UNIQUE INDEX workouts_clients on workouts (client_id);

CREATE TABLE dict_type_exercises
(
    id char(8) PRIMARY KEY DEFAULT generateid(),
    name varchar(64) NOT NULL,
    description varchar(200)
);

CREATE TABLE exercises
(
    id char(8) PRIMARY KEY DEFAULT generateid(),
    type_id char(8) REFERENCES dict_type_exercises(id),
    counting text

);
CREATE UNIQUE INDEX exercises_id on exercises (id);

COMMIT;