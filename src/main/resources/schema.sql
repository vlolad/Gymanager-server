DROP FUNCTION IF EXISTS generateid CASCADE;
DROP TABLE IF EXISTS _users_roles CASCADE;
DROP TABLE IF EXISTS _roles CASCADE;
DROP TABLE IF EXISTS _users CASCADE;
DROP SEQUENCE IF EXISTS main_id_sequence;

BEGIN;

-- Author: https://github.com/PavelProjects
CREATE SEQUENCE main_id_sequence;

create function generateid()
    returns char(8) as
'
    declare
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

CREATE TABLE _users
(
    id       char(8) PRIMARY KEY DEFAULT generateid(),
    name     varchar(32)  NOT NULL,
    login    varchar(32)  NOT NULL UNIQUE,
    email    varchar(128) NOT NULL UNIQUE,
    password varchar(128) NOT NULL
);

CREATE TABLE _roles
(
    id   char(8) PRIMARY KEY DEFAULT generateid(),
    name varchar(32) NOT NULL
);

CREATE TABLE _users_roles
(
    user_id char(8) REFERENCES _users (id) NOT NULL,
    role_id char(8) REFERENCES _roles (id) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);

COMMIT;