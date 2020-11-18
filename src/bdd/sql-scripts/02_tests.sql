CREATE TABLE test (
    project int,
    id int AUTO_INCREMENT,
    name varchar(50),
    description varchar(500),
    lastExecution DATETIME,
    state ENUM('validate', 'refused', 'not executed'),

    PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id)
);