CREATE TABLE dod (
    project int NOT NULL,
    task int NOT NULL,
    id int AUTO_INCREMENT,
    description TEXT,
    state boolean,

    CONSTRAINT PK_ID PRIMARY KEY (id),
    FOREIGN KEY (project, task) REFERENCES task(project, id) ON DELETE CASCADE
);