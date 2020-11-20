-- Create projects 1 and 2

INSERT INTO project (name, description)
VALUES ('cdp-g1-eq1', 'Projet pour le cours de Conduite de projet'), 
    ('projet concour photo', 'Projet pour le cours de programmation web');


-- Add 2 tests to each project

INSERT INTO test (name, description, lastExecution, state, project)
VALUES ('test1 p1', 'test1 p1 description', NULL, 'not executed', 1), 
    ('test2 p1', 'test2 p1 description', NULL, 'not executed', 1), 
    ('test1 p2', 'test1 p2 description', NULL, 'not executed', 2), 
    ('test2 p2', 'test2 p2 description', NULL, 'not executed', 2);

-- Add 3 us to the first project, 1 us to the second us

INSERT INTO us (project, description, priority, difficulty)
VALUES (1, "Premiere US", "High", 1),
    (1, "Seconde US", "Medium", 3),
    (1, "Troisieme US", "High", 2),
    (2, "Premiere US du second projet", "Low", 5);


INSERT INTO task (project, title, duration, status, us)
VALUES (1, "Faire un truc", "3hh", "TODO", 1),
    (1, "Faire un autre truc", "2hh", "TODO", 1),
    (1, "Faire un truc pour l'autre truc", "3hh", "TODO", 2),
    (2, "Faire le premier truc", "1d", "DOING", 1),
    (2, "Faire un truc on sait pas pourquoi encore", "", "TODO", null);