package dao.sql;

import domain.Sprint;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLSprintDAOTest {

    @Test
    void testSimpleInsert() {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1,"I am a sprint");

        assertDoesNotThrow(() -> sprintDAO.insert(sprint));
    }

    @Test
    void testSimpleInsertTwice()  {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1, "I am a sprint 2");

        assertDoesNotThrow(() -> sprintDAO.insert(sprint));
        assertDoesNotThrow(() -> sprintDAO.insert(sprint));
    }

    @Test
    void testInsert() throws Exception {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1, "I am a sprint 3");

        Sprint insertedSprint = sprintDAO.insert(sprint);

        assertEquals(sprint.name, insertedSprint.name);
        assertEquals(sprint.projectId, sprint.projectId);
    }

    @Test
    void testUpdate() throws Exception {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1, "I am a sprint and i need an update");

        Sprint inserted = sprintDAO.insert(sprint);
        
        Sprint updateSprint = new Sprint(inserted.projectId, "i have a new name", inserted.id);
        sprintDAO.update(updateSprint);

        Sprint updatedSprint = sprintDAO.getById(updateSprint.projectId, updateSprint.id);

        assertTrue(updateSprint.equals(updatedSprint));
    }
}