INSERT INTO teachers (first_name, last_name, age) VALUES ('fistName1', 'lastName1', 20);
INSERT INTO teachers (first_name, last_name, age) VALUES ('fistName2', 'lastName2', 25);

INSERT INTO groups (name) VALUES ('groupNameForTest');
INSERT INTO groups (name) VALUES ('groupNameForTest');

INSERT INTO courses (name, teacher_id) VALUES ('math', 1);
INSERT INTO courses (name, teacher_id) VALUES ('economy', 2);

INSERT INTO students (first_name, last_name, age, group_id) VALUES ('fistName1', 'lastName1', 20, 1);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('fistName2', 'lastName2', 21, 1);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('fistName3', 'lastName3', 23, 2);
INSERT INTO students (first_name, last_name, age, group_id) VALUES ('fistName4', 'lastName4', 24, 2);

INSERT INTO groups_courses (group_id, course_id) VALUES (1, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (2, 2);

INSERT INTO students_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (4, 2);