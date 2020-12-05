CREATE TABLE release_us (
    project int,
    `release` int,
    us int,

    CONSTRAINT PK_ID PRIMARY KEY (project, `release`, us),
    FOREIGN KEY (project, us) REFERENCES us(project, id) ON DELETE CASCADE,
    FOREIGN KEY (`release`) REFERENCES `release`(id) ON DELETE CASCADE
);