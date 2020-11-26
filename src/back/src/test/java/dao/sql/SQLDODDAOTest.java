package dao.sql;

import domain.DOD;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLDODDAOTest {
    @Test
    void testSimpleInsert() {
        SQLDODDAO DODDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        assertDoesNotThrow(() -> DODDAO.insert(dod));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLDODDAO().getAllForProject(1));
        assertDoesNotThrow(() -> new SQLDODDAO().getAllForTask(1,1));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLDODDAO DODDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        assertDoesNotThrow(() -> DODDAO.insert(dod));
        assertDoesNotThrow(() -> DODDAO.insert(dod));
    }

    @Test
    void testInsert() throws Exception {
        SQLDODDAO DODDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        DOD inserted = DODDAO.insert(dod);

        assertFalse(dod.equals(inserted));
        assertEquals(dod.description, dod.description);
        assertEquals(dod.projectId, dod.projectId);
        assertEquals(dod.taskId, dod.taskId);
        assertEquals(dod.state, dod.state);
    }

    @Test
    void testUpdate() throws Exception {
        SQLDODDAO DODDAO = new SQLDODDAO();
        DOD dod = new DOD(1, 1, "Description de la DOD", false);

        DOD inserted = DODDAO.insert(dod);
        
        DOD updateDod = new DOD(inserted.projectId, inserted.taskId, "New desc", !inserted.state, inserted.id);
        DODDAO.update(updateDod);

        DOD updatedDod = DODDAO.getById(updateDod.id);

        assertTrue(updateDod.equals(updatedDod));
    }
}