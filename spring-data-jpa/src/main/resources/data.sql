/************************************************************
 ************************** GLOBAL **************************
 ************************************************************/

/* 
 ADDRESS ENTITY 
 */
INSERT INTO address (postal_code, street_name, building_number, complement, neighborhood, city, state_or_region, country)
VALUES ('28911160', 'Rua Belo Horizonte', '965', null, 'Palmeiras', 'Cabo Frio', 'Rio de Janeiro', 'Brasil'),
       ('31742308', 'Avenida Lucas de Oliveira', '1802', null, 'Jardim Guanabara', 'Belo Horizonte', 'Mato Grosso', 'Brasil'),
       ('32667606', 'Rua Lucas da Silva Santana', '2500', null, 'Paulo Camilo','Betim', 'Mato Grosso', 'Brasil'),
       ('23028560', 'Avenida Nelson Moura Brasil do Amaral', '1670', null, 'Guaratiba', 'Rio de Janeiro', 'Rio de Janeiro', 'Brasil'),
       ('26170280', 'Rua Brasilia', '990', null, 'Pauline', 'Belford Roxo', 'Rio de Janeiro', 'Brasil');

/*
 COMPANY ENTITY
 */
INSERT INTO company (id, name, segment, address_postal_code)
VALUES (1001, 'Roogle', 'IT Services', '32667606'),
       (1002, 'Emazon', 'IT Services', '23028560'),
       (1003, 'Hervard University', 'University', '26170280');

/*
 PERSON ENTITY
 */
INSERT INTO person (id, first_name, last_name, address_postal_code)
VALUES (1, 'Victor', 'Marcos', '28911160'),
       (2, 'Pedro', 'Henrique', '28911160'),
       (3, 'Sandro', 'Batista', '31742308');

/*
 COMPANY_EMPLOYEE ENTITY
 */
INSERT INTO company_employee (company_id, person_id)
VALUES (1001, 1),
       (1001, 2),
       (1002, 2),
       (1002, 3);

/************************************************************
 ************************ EDUCATION *************************
 ************************************************************/

/*
 UNIVERSITY ENTITY
 */
INSERT INTO university (id)
VALUES (1003);

/*
 COURSE ENTITY
 */
INSERT INTO course (id, name, university_id)
VALUES (1, 'Computer Science', 1003);

/*
 SUBJECT ENTITY
 */
INSERT INTO subject (id, name)
VALUES (101, 'Computer Architecture'),
       (102, 'Computer Programming'),
       (103, 'Operational Systems'),
       (104, 'Data Structure I'),
       (105, 'Programming Language'),
       (106, 'Entrepreneurship'),
       (107, 'Database'),
       (108, 'Data Structure II'),
       (109, 'Software Engineering');

/*
 CURRICULUM_GRADE ENTITY
 */
INSERT INTO curriculum_grade (id, course_id, status)
VALUES (11, 1, 'active');

/*
 CURRICULUM_GRADE ENTITY
 */
INSERT INTO curriculum_grade_item (curriculum_grade_id, subject_id, workload, stage)
VALUES (11, 101, 72, 1),
       (11, 102, 72, 1),
       (11, 106, 72, 2),
       (11, 104, 72, 2),
       (11, 103, 36, 1),
       (11, 105, 36, 2),
       (11, 107, 72, 3),
       (11, 108, 72, 3),
       (11, 109, 36, 3);

/*
 STUDENT ENTITY
 */
INSERT INTO student (id)
VALUES (1), (2), (3);

/*
 STUDENT_ENROLLMENT ENTITY
 */
INSERT INTO student_enrollment (student_id, curriculum_grade_id, enrollment_date, status)
VALUES (1, 11, CURRENT_TIMESTAMP, 'active'), (3, 11, CURRENT_TIMESTAMP - 15, 'active');