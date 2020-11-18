CREATE TABLE test (
    project int,
    id int,
    name VARCHAR(50),
    description VARCHAR(500),
    lastExecution DATETIME,
    state ENUM('validate', 'refused', 'not executed'),

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id)
);

DELIMITER |
CREATE TRIGGER CK_TEST BEFORE INSERT ON test FOR EACH ROW 
BEGIN
    SET NEW.id = IFNULL((SELECT MAX(id) FROM test WHERE project = NEW.project),0) + 1;
END| 
DELIMITER ;