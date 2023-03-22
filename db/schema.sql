BEGIN;
--Стоит добавить в каждую таблицу:
-- Когда создано
-- Кем создано
-- Когда изменено
-- Кем изменено


-- СОХРАНЯЕМ ОБЩИЙ СТИЛЬ
-- Любая таблица, индекс, счетчик начинаются с gm_
-- Не плодим лишние индексы
-- Добавляем пояснения, что за таблицы


-- Author: https://github.com/PavelProjects
CREATE SEQUENCE gm_main_id_sequence;

create function generateid()
    returns char(10) as
'
    declare
        -- add prefix for server identify
        str       text := ''0123456789abcdefghijklmnopqrstuvwxyz'';
        val       bigint;
        id_       text;
        mod       int;
        server_id text := ''01'';
    begin
        val := nextval(''gm_main_id_sequence'');
        id_ := '''';
        while (length(id_) < 8)
            loop
                mod = val % 36;
                id_ := substring(str, mod + 1, 1) || id_;
                val = val / 36;
            end loop;
        return server_id || id_;
        return ''null'';
    end;
'
    language plpgsql;

-- Таблица юзеров. На первых этапах - тренера
CREATE TABLE gm_users
(
    id            char(10) PRIMARY KEY  DEFAULT generateid(),
    creation_date timestamp    not null default now(),
--    Возможно нужно добавить создание индекса для логина -> ускорит поиск
    login         varchar(32)  NOT NULL UNIQUE,
    first_name    varchar(32)  NOT NULL,
    middle_name   varchar(32),
    last_name     varchar(32),
    email         varchar(128) UNIQUE,
    phone         char(11)     NOT NULL UNIQUE,
    password      varchar(128) NOT NULL
);

CREATE TABLE gm_roles
(
    id   char(10) PRIMARY KEY DEFAULT generateid(),
    name varchar(32) NOT NULL
);

CREATE TABLE gm_users_roles
(
    user_id       char(10) REFERENCES gm_users (id) NOT NULL,
    role_id       char(10) REFERENCES gm_roles (id) NOT NULL,
    creation_date timestamp                         not null default now(),
    CONSTRAINT gm_pk_users_roles UNIQUE (user_id, role_id)
);

CREATE TABLE gm_clients
(
    id            char(10) PRIMARY KEY DEFAULT generateid(),
    creation_date timestamp   not null default now(),
    first_name    varchar(32) NOT NULL,
    middle_name   varchar(32),
    last_name     varchar(32),
    phone         char(11)    not null UNIQUE,
    birthday      date,
    description   varchar(1024)
);

-- Таблица связи тренера и клиента. Клиент может ходить к разным тренерам.
CREATE TABLE gm_trainer_clients
(
    id              char(10) PRIMARY KEY DEFAULT generateid(),
    creation_date   timestamp not null   default now(),
    trainer_user_id char(10)  NOT NULL REFERENCES gm_users (id),
    client_id       char(10)  NOT NULL REFERENCES gm_clients (id),
    CONSTRAINT gm_pk_trainer_client UNIQUE (trainer_user_id, client_id)
);

-- Таблица с инфой о тренировке
-- Тренировка может быть создана на будущее
CREATE TABLE gm_workouts
(
    id              char(10) PRIMARY KEY DEFAULT generateid(),
    trainer_user_id char(10) NOT NULL REFERENCES gm_users (id),
    client_id       char(10) NOT NULL REFERENCES gm_clients (id),
    start_date      timestamp,
    description     text
);
CREATE INDEX gm_ind_workouts_clients on gm_workouts (client_id);

-- Справочник единиц измерения (кол-во, секунды и тп)
create table gm_dict_measures
(
    id          char(10) PRIMARY KEY DEFAULT generateid(),
    system_name varchar(64) NOT NULL,
    caption     varchar(64) not null,
    units       varchar(32)
);

-- Справочник упражнений
CREATE TABLE gm_dict_exercises
(
    id              char(10) PRIMARY KEY DEFAULT generateid(),
    system_name     varchar(64) NOT NULL unique,
    caption         varchar(64) not null,
    description     varchar(200),
    measure_type_id char(10)    NOT NULL REFERENCES gm_dict_measures (id)
);
CREATE INDEX gm_ind_dict_exercise_name on gm_dict_exercises (caption);

-- Связь упражнения и тренировки
CREATE TABLE gm_workout_exercises
(
    id         char(10) PRIMARY KEY DEFAULT generateid(),
    workout_id char(10) NOT NULL REFERENCES gm_workouts (id),
    type_id    char(10) NOT NULL REFERENCES gm_dict_exercises (id),
    note       text
);
create index gm_ind_workout_exercises_wrk_id on gm_workout_exercises (workout_id);

-- Информация о подходе упражнения
CREATE TABLE  gm_exercise_results
(
    id         char(10) PRIMARY KEY DEFAULT generateid(),
    exercise_id char(10) NOT NULL REFERENCES gm_workout_exercises (id),
    execution_order numeric NOT NULL,
    result numeric NOT NULL
);
create index gm_exercise_results_exc_id on gm_exercise_results (exercise_id);

insert into gm_dict_measures values (generateid(), 'count', 'Количество', 'раз');
insert into gm_dict_measures values (generateid(), 'meter', 'Дистанция в метрах', 'м');

insert into gm_dict_exercises select generateid(), 'pushups', 'Отжимания', 'Отжимания от пола', m.id
  from gm_dict_measures m where system_name = 'count';
insert into gm_dict_exercises select generateid(), 'sprint_one_km', 'Бег', 'Бег 1 километр', m.id
  from gm_dict_measures m where system_name = 'meter';

COMMIT;