CREATE TABLE member (
    user int AUTO_INCREMENT,
    project int,
    role TEXT,
    level TEXT,

    CONSTRAINT PK_ID PRIMARY KEY (user),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE
);

