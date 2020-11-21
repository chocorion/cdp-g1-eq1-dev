package dao.sql;

import domain.UserStory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLTestDAOTest {
    @Test
    void testSimpleInsert() {
        SQLTestDAO testDAO = new SQLTestDAO();
        domain.Test test = new domain.Test("Test important", "Tester que ça marche pas", null, "validate", 1);

        assertDoesNotThrow(() -> testDAO.insert(test));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLUserStoryDAO().getAllForProject(1));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLTestDAO testDAO = new SQLTestDAO();
        domain.Test test = new domain.Test("Test important", "Tester que ça marche pas", null, "validate", 1);

        assertDoesNotThrow(() -> testDAO.insert(test));
        assertDoesNotThrow(() -> testDAO.insert(test));
    }

    @Test
    void testInsert() throws Exception {
        SQLTestDAO testDAO = new SQLTestDAO();
        domain.Test test = new domain.Test("Test important", "Tester que ça marche pas", null, "validate", 1);

        domain.Test inserted = testDAO.insert(test);

        assertFalse(test.equals(inserted));
        assertEquals(test.description, inserted.description);
        assertEquals(test.name, inserted.name);
        assertEquals(test.lastExecution, inserted.lastExecution);
        assertEquals(test.state, inserted.state);
        assertEquals(test.projectId, inserted.projectId);
    }
}