package dao;

import domain.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SQLProjectDAOTest {
    @Test
    void getById() throws SQLException {
        /*
            First entry in db is :
            |  1 | cdp-g1-eq1 | Projet pour le cours de Conduite de projet |
         */

        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Project project = dao.getById(1);

        assertEquals(project.id, 1);

        assertEquals(project.name, "cdp-g1-eq1");
        assertEquals(project.description, "Projet pour le cours de Conduite de projet");
    }

    @Test
    void testGetAll() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        List<Project> projects = dao.getAll();

        assertNotNull(projects);
        assertEquals(projects.size(), 2);
    }


    @Test
    void insert() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Project project = new Project("new project", "my new project");

        Project projectInserted = dao.insert(project);

        assertNotNull(projectInserted);
        assertNotEquals(projectInserted.id, null);

        assertEquals(project.name, projectInserted.name);
        assertEquals(project.description, projectInserted.description);

        assertThrows(SQLException.class, () -> dao.insert(projectInserted));
    }

    @Test
    void update() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();

        Project project = dao.getById(1);
        final Project project2 = new Project("test", project.description);

        assertDoesNotThrow(() -> dao.update(project));

        Project projectInserted = dao.getById(1);

        assertEquals(project2.name, "test");
        assertEquals(project.description, projectInserted.description);
    }
}