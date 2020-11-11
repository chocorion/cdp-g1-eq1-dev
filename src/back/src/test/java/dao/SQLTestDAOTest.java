package dao;

import domain.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SQLTestDAOTest {
    /*Optional<Test> getById(int id);
    List<Test> getAllForProject(int projectId);
    Optional<Test> addOne(Test test) throws SQLException;
    void updateOne(Test test) throws SQLException;*/

    @Test
    void getById() {
        SQLTestDAO dao = SQLTestDAO.getInstance();

        Optional<domain.Test> optionalTest = dao.getById(1);

        assertTrue(optionalTest.isPresent());
        domain.Test test = optionalTest.get();

        assertEquals(test.getName(), "test1 p1");
        assertEquals(test.getDescription(), "test1 p1 description");
        assertNull(test.getLastExecution());
        assertEquals(test.getState(), "not executed");
        assertEquals(test.getProjectId(), 1);
    }

    @Test
    void getAllForProject() {
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

        Optional<domain.Test> optionalTest = null;
        try {
            optionalTest = dao.addOne(test);

        } catch (SQLException exception) {
            fail("Exception on addOne");
        }

        assertNotNull(optionalTest);
        assertTrue(optionalTest.isPresent());

        domain.Test insertedTest = optionalTest.get();

        assertEquals(test.getProjectId(), insertedTest.getProjectId());
        assertEquals(test.getName(), insertedTest.getName());
        assertEquals(test.getDescription(), insertedTest.getDescription());
        assertEquals(test.getState(), insertedTest.getState());
        assertEquals(test.getLastExecution(), insertedTest.getLastExecution());

        assertThrows(SQLException.class, () -> dao.addOne(insertedTest));
    }

    @Test
    void updateOne() {
        SQLTestDAO dao = SQLTestDAO.getInstance();
        Optional<domain.Test> testOptional = dao.getById(1);
        assertTrue(testOptional.isPresent());

        domain.Test test = testOptional.get();
        test.setName("coucou");

        assertDoesNotThrow(() -> dao.updateOne(test));

        Optional<domain.Test> testInsertedOptional = dao.getById(1);
        assertTrue(testInsertedOptional.isPresent());

        domain.Test testInserted = testInsertedOptional.get();

        assertEquals(test.getProjectId(), testInserted.getProjectId());
        assertEquals(test.getName(), testInserted.getName());
        assertEquals(test.getDescription(), testInserted.getDescription());
        assertEquals(test.getState(), testInserted.getState());
        assertEquals(test.getLastExecution(), testInserted.getLastExecution());
    }
}