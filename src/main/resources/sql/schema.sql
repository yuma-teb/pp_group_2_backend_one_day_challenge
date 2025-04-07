CREATE TABLE IF NOT EXISTS users
(
    user_id         SERIAL PRIMARY KEY,
    full_name       VARCHAR(50)  NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    profile_picture VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS subjects
(
    subject_id   SERIAL PRIMARY KEY,
    subject_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS quizzes
(
    quiz_id     SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    creator_id  INTEGER      NOT NULL,
    creation_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_schedule BOOLEAN,
    time_limit  INT, -- in second
    access_code VARCHAR(20),
    subject_id  INTEGER,
    total_score INT,
    FOREIGN KEY (creator_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS questions
(
    question_id   SERIAL PRIMARY KEY,
    quiz_id       INTEGER NOT NULL,
    question_text TEXT    NOT NULL,
    is_qcm        BOOLEAN NOT NULL,
    points        INT     NOT NULL DEFAULT 1,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS options
(
    option_id   SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL,
    option_text TEXT    NOT NULL,
    is_correct  BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions (question_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX unique_correct_per_question
    ON options (question_id)
    WHERE is_correct = TRUE;

CREATE TABLE IF NOT EXISTS sessions
(
    session_id SERIAL PRIMARY KEY,
    quiz_id    INTEGER NOT NULL,
    host_id    INTEGER NOT NULL,
    start_time TIMESTAMP,
    end_time   TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id) ON DELETE CASCADE,
    FOREIGN KEY (host_id) REFERENCES users (user_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS session_participants
(
    session_id INTEGER   NOT NULL,
    student_id INTEGER   NOT NULL,
    join_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_joined  BOOLEAN            DEFAULT FALSE,
    PRIMARY KEY (session_id, student_id),
    FOREIGN KEY (session_id) REFERENCES sessions (session_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (user_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS join_quiz
(
    join_id       SERIAL PRIMARY KEY,
    quiz_id       INTEGER   NOT NULL,
    student_id    INTEGER   NOT NULL,
    session_id    INTEGER,
    start_time    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_time      TIMESTAMP,
    archive_score FLOAT,
    is_joined     BOOLEAN   NOT NULL DEFAULT FALSE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES sessions (session_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS student_answers
(
    answer_id   SERIAL PRIMARY KEY,
    join_id     INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    option_id   INTEGER NOT NULL,
    FOREIGN KEY (join_id) REFERENCES join_quiz (join_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions (question_id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES options (option_id) ON DELETE CASCADE
);