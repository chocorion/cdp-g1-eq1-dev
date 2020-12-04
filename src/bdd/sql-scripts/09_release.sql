CREATE TABLE `release` (
    project int,
    id int AUTO_INCREMENT,
    title TEXT,
    description TEXT,
    version_major int,
    version_minor int,
    version_patch int,
    link TEXT,
    creation_date DATE,

    CONSTRAINT PK_ID PRIMARY KEY (id),
    FOREIGN KEY (project) REFERENCES project(id) ON DELETE CASCADE
);