CREATE TABLE sprint (
    project int,
    id int,
    name TEXT,

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE
);

DELIMITER |
CREATE TRIGGER CK_SPRINT BEFORE INSERT ON sprint FOR EACH ROW 
BEGIN
    SET NEW.id = IFNULL((SELECT MAX(id) FROM sprint WHERE project = NEW.project),0) + 1;
END| 
DELIMITER ;