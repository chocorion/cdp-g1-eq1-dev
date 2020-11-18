CREATE TABLE project (
    id int AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE,
    description VARCHAR(500),

    CONSTRAINT PK_ID PRIMARY KEY (id)
);

