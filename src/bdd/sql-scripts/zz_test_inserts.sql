-- Create projects 1 and 2

INSERT INTO project (name, description)
VALUES ('cdp-g1-eq1', 'Projet pour le cours de Conduite de projet'), 
    ('projet concour photo', 'Projet pour le cours de programmation web');


-- Add 2 tests to each project

CALL insert_test(1, 'test1 p1', 'test1 p1 description', NULL, 'not executed', @id);
CALL insert_test(1, 'test2 p1', 'test2 p1 description', NULL, 'not executed', @id);
CALL insert_test(2, 'test1 p2', 'test1 p2 description', NULL, 'not executed', @id);
CALL insert_test(2, 'test2 p2', 'test2 p2 description', NULL, 'not executed', @id);

-- Create sprints for the projects

CALL insert_sprint(1,"Sprint 1", @id);
CALL insert_sprint(1,"Le super Sprint", @id);
CALL insert_sprint(2,"Sprint 1", @id);

-- Add 3 us to the first project, 1 us to the second us

CALL insert_us(1, "Premiere US", "High", 1, 1, @id);
CALL insert_us(1, "Seconde US", "Medium", 3, 1, @id);
CALL insert_us(1, "Troisieme US", "High", 2, 2, @id);
CALL insert_us(2, "Premiere US du second projet", "Low", 5, null, @id);

-- Adding new members to project 1 and 2

INSERT INTO member (project, name, role, level)
VALUES (1, 'David', 'product owner', 'senior'),
        (1,'Marc', 'developper back', 'junior'),
       (2, 'Anna', 'developper front', 'junior');

-- Add a few tasks to the first two projects

CALL insert_task(1, "Faire un truc", "3hh", "TODO", 1, 1, @id);
CALL insert_task(1, "Faire un autre truc", "2hh", "TODO", 1, 1, @id);
CALL insert_task(1, "Faire un truc pour l'autre truc", "3hh", "TODO", 2, 1, @id);
CALL insert_task(1, "Faire le premier truc", "1d", "DOING", 1, 2, @id);
CALL insert_task(1, "Check quand la liste est vide", "1d", "DONE", 1, 2, @id);
CALL insert_task(2, "Faire le premier truc", "1d", "DOING", 1, null, @id);
CALL insert_task(2, "Faire un truc on sait pas pourquoi encore", "", "TODO", null, 3, @id);

-- Create some DODs for a few tasks

INSERT INTO dod (project, task, id, description, state)
VALUES (1, 1, 1, "Vérifier que ça marche", 0),
    (1, 1, 2, "Vérifier que ça marche toujours", 0),
    (1, 2, 3, "Vérifier que ça marche", 1),
    (2, 1, 4, "Vérifier que ça marche pas", 1);

-- Add dependencies between tasks

INSERT INTO task_dep (project, parent, child)
VALUES (1, 1, 2),
    (1, 2, 3),
    (2, 1, 2);
