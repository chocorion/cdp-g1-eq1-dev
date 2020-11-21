CREATE TABLE us (
    project int,
    id int,
    description TEXT,
    priority ENUM('Low', 'Medium', 'High'),
    difficulty int,

    CONSTRAINT PK_ID PRIMARY KEY (project, id),
    FOREIGN KEY (project) REFERENCES project(id)
);

DELIMITER |
CREATE TRIGGER CK_US BEFORE INSERT ON us FOR EACH ROW 
BEGIN
    SET NEW.id = IFNULL((SELECT MAX(id) FROM us WHERE project = NEW.project),0) + 1;
    UPDATE last_id SET id = NEW.id;
END| 
DELIMITER ;