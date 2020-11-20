package dao;

import org.junit.jupiter.api.Test;

import dao.sql.SQLProjectDAO;
import domain.Project;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    @Test
    void testSimpleInsert() {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("OCaMl project","I am an OCaMl project");

        assertDoesNotThrow(() -> projectDAO.insert(project));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("Java project","I am a java project");

        assertDoesNotThrow(() -> projectDAO.insert(project));
        assertThrows(SQLException.class, () -> projectDAO.insert(project));
    }

    @Test
    void testInsert() throws SQLException {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = new Project("Python project","I am a Python project");

        Project insertedProject = projectDAO.insert(project);

        assertEquals(project.name, insertedProject.name);
        assertEquals(project.description, project.description);
    }
}