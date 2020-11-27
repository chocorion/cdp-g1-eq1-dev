package dao.sql;

import domain.DOD;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLDODDAOTest {
    @Test
    void testSimpleInsert() {
        SQLDODDAO dodDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        assertDoesNotThrow(() -> dodDAO.insert(dod));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLDODDAO().getAllForProject(1));
        assertDoesNotThrow(() -> new SQLDODDAO().getAllForTask(1, 1));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLDODDAO dodDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        assertDoesNotThrow(() -> dodDAO.insert(dod));
        assertDoesNotThrow(() -> dodDAO.insert(dod));
    }

    @Test
    void testInsert() throws Exception {
        SQLDODDAO dodDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        DOD inserted = dodDAO.insert(dod);

        assertFalse(dod.equals(inserted));
        assertEquals(dod.description, dod.description);
        assertEquals(dod.projectId, dod.projectId);
        assertEquals(dod.taskId, dod.taskId);
        assertEquals(dod.state, dod.state);
    }

    @Test
    void testUpdate() throws Exception {
        SQLDODDAO dodDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        DOD inserted = dodDAO.insert(dod);

        DOD updateDod = new DOD(inserted.projectId, inserted.taskId, "New desc", !inserted.state, inserted.id);
        dodDAO.update(updateDod);

        DOD updatedDod = dodDAO.getById(updateDod.id);

        assertTrue(updateDod.equals(updatedDod));
    }
}