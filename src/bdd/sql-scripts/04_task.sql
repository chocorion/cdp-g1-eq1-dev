CREATE TABLE task (
    project int,
    id int,
    title TEXT,
    duration TEXT,
    status TEXT,  
    us int,

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (project, us) REFERENCES us(project, id)
);

DELIMITER |
CREATE PROCEDURE insert_task(project_ int, title_ text, duration_ text, status_ text, us_ int, OUT id_ int) 
BEGIN 
    SET id_ = IFNULL((SELECT MAX(id) FROM task WHERE project = project_),0) + 1; 
    INSERT INTO task (project, id, title, duration, status, us) VALUES (project_, id_, title_, duration_, status_, us_); 
END; |
DELIMITER ;