DROP SCHEMA IF EXISTS public CASCADE ;
CREATE SCHEMA public;

CREATE TABLE users (
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) ,
    age INT NOT NULL ,
    user_photo bytea ,
    contacts integer[]
);

CREATE TABLE groups (
    id SERIAL PRIMARY KEY ,
    user_id INT DEFAULT 1 ,
    type VARCHAR(255) NOT NULL UNIQUE ,
    max_score int DEFAULT 0,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE person (
    id SERIAL,
    user_id INT DEFAULT 1,
    group_id int DEFAULT 1,
    name VARCHAR(255) NOT NULL UNIQUE ,
    age INT NOT NULL,
    photo bytea NOT NULL,
    memo TEXT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET DEFAULT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE results (
     id SERIAL primary key ,
     user_id int default 1 ,
     played_time timestamp NOT NULL ,
     quiz_type VARCHAR NOT NULL ,
     group_id int DEFAULT 1 ,
     score int CHECK ( score between 0 and 10) NOT NULL ,
     people integer[] NOT NULL ,
     CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups(id),
     CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION update_group_max_score_func()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE groups SET max_score = (
        SELECT max(score)
        FROM results
    )
    WHERE id = new.group_id;
    RETURN new;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_group_max_score
    AFTER INSERT OR UPDATE ON results
    FOR EACH ROW
    EXECUTE PROCEDURE update_group_max_score_func();
