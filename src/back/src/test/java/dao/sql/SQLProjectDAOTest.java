package dao.sql;

import org.junit.jupiter.api.Test;

import dao.sql.SQLProjectDAO;
import domain.Project;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLProjectDAOTest {
    @Test
    void testSimpleInsertDelete() {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("OCaMl project","I am an OCaMl project");

        assertDoesNotThrow(() -> projectDAO.delete(projectDAO.insert(project)));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("Java project","I am a java project");

        Project insertedProject = (Project) assertDoesNotThrow(() -> projectDAO.insert(project));
        assertThrows(SQLException.class, () -> projectDAO.insert(project));
        assertDoesNotThrow(() -> projectDAO.delete(insertedProject));
    }

    @Test
    void testInsert() throws SQLException {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("Python project","I am a Python project");

        Project insertedProject = projectDAO.insert(project);

        assertEquals(project.name, insertedProject.name);
        assertEquals(project.description, project.description);

        try {
            projectDAO.delete(insertedProject);
        } catch(Exception exception) {
            // We don't want this to fail the test
            // It is to be able to launch the tests twice in a row
        }
    }

    @Test
    void testUpdate() throws Exception {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("update project","update me !");

        Project inserted = projectDAO.insert(project);
        
        Project updateProject = new Project("brand new project", "updated cool", inserted.id);
        projectDAO.update(updateProject);

        Project updatedProject = projectDAO.getById(updateProject.id);

        assertTrue(updateProject.equals(updatedProject));
        assertDoesNotThrow(() -> projectDAO.delete(inserted));
    }
}