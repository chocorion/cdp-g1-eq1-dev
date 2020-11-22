CREATE TABLE test (
    project int,
    id int,
    name VARCHAR(50),
    description VARCHAR(500),
    lastExecution DATETIME,
    state ENUM('validate', 'refused', 'not executed'),

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE
);

DELIMITER |
CREATE PROCEDURE insert_test(project_ int, name_ text, description_ text, lastExecution_ DATETIME, state_ TEXT, OUT id_ int) 
BEGIN 
    SET id_ = IFNULL((SELECT MAX(id) FROM test WHERE project = project_),0) + 1; 
    INSERT INTO test (project, id, name, description, lastExecution, state) VALUES (project_, id_, name_, description_, lastExecution_, state_); 
END; |
DELIMITER ;