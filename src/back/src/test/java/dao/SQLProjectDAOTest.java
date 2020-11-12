package dao;

import domain.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLProjectDAOTest {
    @Test
    void getById() throws SQLException {
        /*
            First entry in db is :
            |  1 | cdp-g1-eq1 | Projet pour le cours de Conduite de projet |
         */

        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Project project = dao.getById(1);

        assertEquals(project.getId(), 1);

        assertEquals(project.getName(), "cdp-g1-eq1");
        assertEquals(project.getDescription(), "Projet pour le cours de Conduite de projet");
    }

    @Test
    void testGetAll() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        List<Project> projects = dao.getAll();

        assertNotNull(projects);
        assertEquals(projects.size(), 2);
    }


    @Test
    void insertOne() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Project project = new Project("new project", "my new project");

        Project projectInserted = dao.addOne(project);

        assertNotNull(projectInserted);
        assertNotEquals(projectInserted.getId(), -1);

        assertEquals(project.getName(), projectInserted.getName());
        assertEquals(project.getDescription(), projectInserted.getDescription());

        assertThrows(SQLException.class, () -> dao.addOne(projectInserted));
    }

    @Test
    void updateOne() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();

        Project project = dao.getById(1);
        project.setName("test");

        assertDoesNotThrow(() -> dao.updateOne(project));

        Project projectInserted = dao.getById(1);

        assertEquals(project.getName(), "test");
        assertEquals(project.getDescription(), projectInserted.getDescription());
    }
}