CREATE TABLE task_dep (
    project int,
    parent int,
    child int,

    CONSTRAINT PK_ID PRIMARY KEY (project, parent, child),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (project, parent) REFERENCES task(project, id) ON DELETE CASCADE,
    FOREIGN KEY (project, child) REFERENCES task(project, id) ON DELETE CASCADE
);