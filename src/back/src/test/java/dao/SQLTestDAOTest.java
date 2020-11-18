package dao;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SQLTestDAOTest {
    @Test
    void getById() {
        SQLTestDAO dao = SQLTestDAO.getInstance();

        domain.Test test = null;

        try {
             test = dao.getById(1);
        } catch (SQLException exception) {
            fail(exception.getMessage());
        }
        assertNotNull(test);

        assertEquals(test.name, "test1 p1");
        assertEquals(test.description, "test1 p1 description");
        assertNull(test.lastExecution);
        assertEquals(test.state, "not executed");
        assertEquals(test.projectId, 1);
    }

    @Test
    void getAllForProject() throws SQLException {
        SQLTestDAO dao = SQLTestDAO.getInstance();

        List<domain.Test> tests = dao.getAllForProject(1);

        assertNotNull(tests);
        assertEquals(tests.size(), 2);

        tests = dao.getAllForProject(2);
        assertNotNull(tests);
        assertEquals(tests.size(), 3);
    }

    @Test
    void addOne() {
        SQLTestDAO dao = SQLTestDAO.getInstance();

        domain.Test test = new domain.Test(
                "my test",
                "test nothing",
                null,
                "refused",
                1
        );

        domain.Test insertedTest = null;
        try {
            insertedTest = dao.addOne(test);

        } catch (SQLException exception) {
            fail("Exception on addOne");
        }

        assertNotNull(insertedTest);

        assertEquals(test.projectId, insertedTest.projectId);
        assertEquals(test.name, insertedTest.name);
        assertEquals(test.description, insertedTest.description);
        assertEquals(test.state, insertedTest.state);
        assertEquals(test.lastExecution, insertedTest.lastExecution);

        final domain.Test testF = insertedTest;
        assertThrows(SQLException.class, () -> dao.addOne(testF));
    }

    @Test
    void updateOne() throws SQLException {
        SQLTestDAO dao = SQLTestDAO.getInstance();
        domain.Test test = dao.getById(1);

        domain.Test test2 = new domain.Test("coucou", test.description, test.lastExecution, test.state, test.id, test.projectId);

        assertDoesNotThrow(() -> dao.updateOne(test2));

        domain.Test testInserted = dao.getById(1);

        assertEquals(test.projectId, testInserted.projectId);
        assertNotEquals(test.name, testInserted.name);
        assertEquals(test.description, testInserted.description);
        assertEquals(test.state, testInserted.state);
        assertEquals(test.lastExecution, testInserted.lastExecution);
    }
}