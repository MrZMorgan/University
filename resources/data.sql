INSERT INTO teachers (first_name, last_name, age) VALUES ('Ivan', 'Smirnov', 20);
INSERT INTO teachers (first_name, last_name, age) VALUES ('Oleg', 'Sidorov', 25);

INSERT INTO groups (name) VALUES ('GS-10-1');
INSERT INTO groups (name) VALUES ('ERB-11-2');

INSERT INTO courses (name, teacher_id) VALUES ('math', 1);
INSERT INTO courses (name, teacher_id) VALUES ('economy', 2);

INSERT INTO students (first_name, last_name, age, group_id) VALUES ('Egor', 'Anchutin', 20, 1);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('Guinea', 'Pig', 21, 1);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('Olga', 'Petrova', 23, 2);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('Yaroslava', 'Igorevna', 24, 2);

INSERT INTO groups_courses (group_id, course_id) VALUES (1, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (2, 2);

INSERT INTO students_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (4, 2);