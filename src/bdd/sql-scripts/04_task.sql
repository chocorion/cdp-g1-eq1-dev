CREATE TABLE task (
    project int,
    id int,
    title TEXT,
    duration TEXT,
    status TEXT,  
    us int,

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project, us) REFERENCES us(project, id)
);

DELIMITER |
CREATE TRIGGER CK_TASK BEFORE INSERT ON task FOR EACH ROW 
BEGIN
    SET NEW.id = IFNULL((SELECT MAX(id) FROM task WHERE project = NEW.project),0) + 1;
    UPDATE last_id SET id = NEW.id;
END| 
DELIMITER ;