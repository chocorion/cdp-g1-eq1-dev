package dao.sql;

import domain.Sprint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLSprintDAOTest {

    @Test
    void testSimpleInsert() {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1, "I am a sprint");

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
        Sprint sprint = new Sprint(1, "I am a sprint 3", "archived");

        Sprint insertedSprint = sprintDAO.insert(sprint);

        assertEquals(sprint.name, insertedSprint.name);
        assertEquals(sprint.projectId, insertedSprint.projectId);
        assertEquals(sprint.state, insertedSprint.state);
    }

    @Test
    void testUpdate() throws Exception {
        SQLSprintDAO sprintDAO = new SQLSprintDAO();
        Sprint sprint = new Sprint(1, "I am a sprint and i need an update");

        Sprint inserted = sprintDAO.insert(sprint);

        Sprint updateSprint = new Sprint(inserted.projectId, "i have a new name", "actif", inserted.id);
        sprintDAO.update(updateSprint);


        assertEquals(
                sprintDAO.getById(updateSprint.projectId, updateSprint.id),
                updateSprint
        );
    }
}