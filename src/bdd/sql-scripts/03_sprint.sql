CREATE TABLE sprint (
    project int,
    id int,
    name TEXT,
    state ENUM('pending', 'active', 'archived'),

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE
);

DELIMITER |
CREATE PROCEDURE insert_sprint(project_ int, name_ text, state_ TEXT, OUT id_ int) 
BEGIN 
    SET id_ = IFNULL((SELECT MAX(id) FROM sprint WHERE project = project_),0) + 1; 
    INSERT INTO sprint (project, id, name, state) VALUES (project_, id_, name_, state_); 
END; |
DELIMITER ;