DROP SCHEMA IF EXISTS public CASCADE ;
CREATE SCHEMA public;

CREATE TABLE person (
    id SERIAL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    photo bytea NOT NULL,
    voice bytea NOT NULL,
    memo TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE quiz (
    id SERIAL primary key,
    max_score int DEFAULT 0,
    last_played timestamp,
    times_played int DEFAULT 0
);

CREATE TABLE results (
     id SERIAL primary key ,
     quiz_id int,
     played_time timestamp ,
     score int CHECK ( score between 0 and 10),
     CONSTRAINT fk_quiz FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
 );

CREATE OR REPLACE FUNCTION update_times_played_func()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE quiz SET times_played = (
        SELECT count(*)
        FROM results
        WHERE quiz_id = new.quiz_id
    )
    WHERE id = new.quiz_id;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_times_played
    AFTER INSERT ON results
    FOR EACH ROW
    EXECUTE PROCEDURE update_times_played_func();

CREATE OR REPLACE FUNCTION update_quiz_max_score_func()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE quiz SET max_score = (
        SELECT max(score)
        FROM results
        where quiz_id = new.quiz_id
    )
    WHERE id = new.quiz_id;
    RETURN new;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_quiz_max_score
    AFTER INSERT OR UPDATE ON results
    FOR EACH ROW
    EXECUTE PROCEDURE update_quiz_max_score_func();

CREATE TABLE people_quiz_relation (
    quiz_id int,
    person_id int,
    PRIMARY KEY (quiz_id, person_id),
    CONSTRAINT fk_quiz FOREIGN KEY(quiz_id) REFERENCES quiz(id) ON DELETE CASCADE,
    CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(id) ON DELETE CASCADE
)
