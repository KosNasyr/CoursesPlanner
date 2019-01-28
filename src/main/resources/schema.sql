
CREATE TABLE COURSE(
  id                      BIGINT         NOT NULL auto_increment,
  course_title            VARCHAR(255)   NOT NULL,
  beginning               DATE           NOT NULL,
  ending                  DATE           NOT NULL,
  days_count              BIGINT         NOT NULL,
);
ALTER TABLE COURSE ADD CONSTRAINT COURSE_ID_PK PRIMARY KEY (id);

CREATE TABLE PROGRESS(
  id                      BIGINT         NOT NULL auto_increment,
  student_id         BIGINT         NOT NULL,
  course_id           BIGINT         NOT NULL,
  marks                ARRAY
);
ALTER TABLE PROGRESS ADD CONSTRAINT PROGRESS_ID_PK PRIMARY KEY (id);
ALTER TABLE PROGRESS ADD FOREIGN KEY (course_id)REFERENCES COURSE(id);

CREATE TABLE STUDENT(
  id                      BIGINT         NOT NULL auto_increment,
  full_name              VARCHAR(255)   NOT NULL,
  email                   VARCHAR(255)   NOT NULL,
  tel_number              BIGINT         NOT NULL,
  password                VARCHAR(255)   ,
  role                          VARCHAR(255)
);

ALTER TABLE STUDENT ADD CONSTRAINT STUDENT_ID_PK PRIMARY KEY (id);

CREATE TABLE STUDENT_COURSE(
  student_id             BIGINT         NOT NULL,
  course_id              BIGINT         NOT NULL,
);

ALTER TABLE STUDENT_COURSE ADD FOREIGN KEY (student_id) REFERENCES STUDENT(id);
ALTER TABLE STUDENT_COURSE ADD FOREIGN KEY (course_id) REFERENCES COURSE(id);

