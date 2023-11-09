DROP SCHEMA IF EXISTS public CASCADE ;
CREATE SCHEMA public;

CREATE TABLE groups (
    id SERIAL PRIMARY KEY ,
    type VARCHAR(255) NOT NULL ,
    max_score int DEFAULT 0
);

CREATE TABLE person (
    id SERIAL,
    group_id int DEFAULT 1,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    photo bytea NOT NULL,
    memo TEXT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET DEFAULT
);

CREATE TABLE results (
     id SERIAL primary key ,
     played_time timestamp NOT NULL ,
     quiz_type VARCHAR NOT NULL ,
     group_id int DEFAULT 1 ,
     score int CHECK ( score between 0 and 10) NOT NULL ,
     people integer[] NOT NULL ,
     CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE OR REPLACE FUNCTION update_quiz_max_score_func()
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

CREATE OR REPLACE TRIGGER update_quiz_max_score
    AFTER INSERT OR UPDATE ON results
    FOR EACH ROW
    EXECUTE PROCEDURE update_quiz_max_score_func();
