INSERT INTO project (name, description)
VALUES ('cdp-g1-eq1', 'Projet pour le cours de Conduite de projet'), 
    ('projet concour photo', 'Projet pour le cours de programmation web');

INSERT INTO test (name, description, lastExecution, state, project)
VALUES ('test1 p1', 'test1 p1 description', NULL, 'not executed', 1), 
    ('test2 p1', 'test2 p1 description', NULL, 'not executed', 1), 
    ('test1 p2', 'test1 p2 description', NULL, 'not executed', 2), 
    ('test2 p2', 'test2 p2 description', NULL, 'not executed', 2);