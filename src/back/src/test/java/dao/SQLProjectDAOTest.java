package dao;

import domain.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SQLProjectDAOTest {
    @Test
    void getById() {
        /*
            First entry in db is :
            |  1 | cdp-g1-eq1 | Projet pour le cours de Conduite de projet |
         */

        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Optional<Project> projectOptional = dao.getById(1);

        assertNotNull(projectOptional);
        assertTrue(projectOptional.isPresent());

        Project project = projectOptional.get();

        assertTrue(project.getId().isPresent());
        assertEquals(project.getId().get(), 1);

        assertEquals(project.getName(), "cdp-g1-eq1");
        assertEquals(project.getDescription(), "Projet pour le cours de Conduite de projet");
    }

    @Test
    void testGetAll() {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        List<Project> projects = dao.getAll();

        assertNotNull(projects);
        assertEquals(projects.size(), 2);
    }


    @Test
    void insertOne() throws SQLException {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Project project = new Project("new project", "my new project");

        Optional<Project> projectOptional = dao.addOne(project);

        assertNotNull(projectOptional);
        assertTrue(projectOptional.isPresent());


        Project projectInserted = projectOptional.get();
        assertTrue(projectInserted.getId().isPresent());

        assertEquals(project.getName(), projectInserted.getName());
        assertEquals(project.getDescription(), projectInserted.getDescription());

        assertThrows(SQLException.class, () -> dao.addOne(projectInserted));
    }

    @Test
    void updateOne() {
        SQLProjectDAO dao = SQLProjectDAO.getInstance();
        Optional<Project> projectOptional = dao.getById(1);
        assertTrue(projectOptional.isPresent());

        Project project = projectOptional.get();
        project.setName("test");

        assertDoesNotThrow(() -> dao.updateOne(project));

        Optional<Project> projectInsertedOptional = dao.getById(1);
        assertTrue(projectInsertedOptional.isPresent());

        Project projectInserted = projectInsertedOptional.get();

        assertEquals(project.getName(), "test");
        assertEquals(project.getDescription(), projectInserted.getDescription());
    }
}