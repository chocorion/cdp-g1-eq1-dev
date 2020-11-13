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

        assertEquals(test.getName(), "test1 p1");
        assertEquals(test.getDescription(), "test1 p1 description");
        assertNull(test.getLastExecution());
        assertEquals(test.getState(), "not executed");
        assertEquals(test.getProjectId(), 1);
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

        assertEquals(test.getProjectId(), insertedTest.getProjectId());
        assertEquals(test.getName(), insertedTest.getName());
        assertEquals(test.getDescription(), insertedTest.getDescription());
        assertEquals(test.getState(), insertedTest.getState());
        assertEquals(test.getLastExecution(), insertedTest.getLastExecution());

        final domain.Test testF = insertedTest;
        assertThrows(SQLException.class, () -> dao.addOne(testF));
    }

    @Test
    void updateOne() throws SQLException {
        SQLTestDAO dao = SQLTestDAO.getInstance();
        domain.Test test = dao.getById(1);

        test.setName("coucou");

        assertDoesNotThrow(() -> dao.updateOne(test));

        domain.Test testInserted = dao.getById(1);

        assertEquals(test.getProjectId(), testInserted.getProjectId());
        assertEquals(test.getName(), testInserted.getName());
        assertEquals(test.getDescription(), testInserted.getDescription());
        assertEquals(test.getState(), testInserted.getState());
        assertEquals(test.getLastExecution(), testInserted.getLastExecution());
    }
}