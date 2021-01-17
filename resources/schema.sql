CREATE TABLE teachers (
                          id INT auto_increment NOT NULL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          age INT NOT NULL
);

CREATE TABLE courses (
                         id INT auto_increment NOT NULL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         teacher_id INT REFERENCES teachers(id)
);

CREATE TABLE groups (
                        id INT auto_increment NOT NULL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL
);

CREATE TABLE students (
                          id INT auto_increment NOT NULL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          age INT NOT NULL,
                          group_id INT REFERENCES groups(id)
);

CREATE TABLE students_courses(
                                 student_id INT REFERENCES students(id),
                                 course_id INT REFERENCES courses(id)
);

CREATE TABLE groups_courses(
                               group_id INT REFERENCES groups(id),
                               course_id INT REFERENCES courses(id)
);