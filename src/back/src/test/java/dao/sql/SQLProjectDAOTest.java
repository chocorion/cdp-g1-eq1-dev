package dao.sql;

import org.junit.jupiter.api.Test;

import domain.*;

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

    @Test
    void testDeleteCascadeProject() throws Exception {
        SQLProjectDAO projectDAO = new SQLProjectDAO();
        Project project = projectDAO.insert(new Project("test cascade delete","project"));
        
        Member member = new SQLMemberDAO().insert(new Member(project.id, "Jiji", "owner", "senior"));
        Sprint sprint = new SQLSprintDAO().insert(new Sprint(project.id,"I am a sprint"));
        UserStory us = new SQLUserStoryDAO().insert(new UserStory(project.id, "US trop bien", "High", 2, sprint.id));
        Task t1 = new SQLTaskDAO().insert(new Task(project.id, us.id, member.user, "tache 1", "1hh", "TODO"));
        Task t2 = new SQLTaskDAO().insert(new Task(project.id, us.id, member.user, "tache 2", "1hh", "TODO"));
        domain.Test test = new SQLTestDAO().insert(new domain.Test("test", "test un test", null, "validate", project.id));
        new SQLTaskDAO().addDependency(t1, t2);

        assertDoesNotThrow(() -> projectDAO.delete(project));

        assertThrows(SQLException.class, () -> new SQLMemberDAO().getById(project.id, member.user));
        assertThrows(SQLException.class, () -> new SQLSprintDAO().getById(project.id, sprint.id));
        assertThrows(SQLException.class, () -> new SQLUserStoryDAO().getById(project.id, us.id));
        assertThrows(SQLException.class, () -> new SQLTaskDAO().getById(project.id, t1.id));
        assertThrows(SQLException.class, () -> new SQLTaskDAO().getById(project.id, t2.id));
        assertThrows(SQLException.class, () -> new SQLTestDAO().getById(project.id, test.id));
        assertTrue(new SQLTaskDAO().getChildrenTasks(t1).isEmpty());
    }
}