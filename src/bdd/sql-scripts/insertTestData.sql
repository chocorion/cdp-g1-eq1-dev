INSERT INTO projects (name, description)
VALUES (
    'cdp-g1-eq1',
    'Projet pour le cours de Conduite de projet'
), (
    'projet concour photo',
    'Projet pour le cours de programmation web'
);

INSERT INTO tests (name, description, lastExecution, state, project_id)
VALUES (
    'test1 p1',
    'test1 p1 description',
    NULL,
    'not executed',
    1
), (
    'test2 p1',
    'test2 p1 description',
    NULL,
    'not executed',
    1
), (
    'test1 p2',
    'test1 p2 description',
    NULL,
    'not executed',
    2
), (
    'test2 p2',
    'test2 p2 description',
    NULL,
    'not executed',
    2
), (
    'test4 p2',
    'test2 p2 description',
    NULL,
    'not executed',
    2
), (
    'test5 p2',
    'test2 p2 description',
    NULL,
    'not executed',
    2
), (
    'test3 p2',
    'test3 p2 description',
    NULL,
    'not executed',
    2
);