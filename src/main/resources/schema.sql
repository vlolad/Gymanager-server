DROP SEQUENCE IF EXISTS main_id_sequence;
DROP FUNCTION IF EXISTS generateid;
DROP TABLE IF EXISTS users;

CREATE SEQUENCE main_id_sequence;

create function generateid() returns char(8) as
    $$
declare
str text := '0123456789abcdefghijklmnopqrstuvwxyz';
    val bigint;
    id_ text;
    mod int;
begin
    val := nextval('main_id_sequence');
    id_ := '';
    while (length(id_) < 8)
        loop
            mod = val % 36;
            id_ := substring(str, mod + 1, 1) || id_;
            val = val / 36;
end loop;
return id_;
return 'null';
end;
$$
language plpgsql;

CREATE TABLE users
(
    id       char(8) PRIMARY KEY DEFAULT generateid(),
    name     varchar(32)  NOT NULL,
    login    varchar(32)  NOT NULL UNIQUE,
    email    varchar(128) NOT NULL UNIQUE,
    password varchar(128) NOT NULL
);

COMMIT;