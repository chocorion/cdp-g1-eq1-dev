package dao.sql;

import domain.Task;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLTaskDAOTest {
    @Test
    void testSimpleInsert() {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(null, 1, 1, "super title", "1h.h", "TODO");

        assertDoesNotThrow(() -> taskDAO.insert(task));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLProjectDAO().getAll());
    }

    @Test
    void testSimpleInsertTwice() {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(null, 1, 1, "super title2", "1h.h", "TODO");
        assertDoesNotThrow(() -> taskDAO.insert(task));
        assertDoesNotThrow(() -> taskDAO.insert(task));
    }

    @Test
    void testInsert() throws Exception {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(null, 1, 1, "super title3", "1h.h", "TODO");

        Task inserted = taskDAO.insert(task);

        assertEquals(task.projectId, inserted.projectId);
        assertEquals(task.usId, inserted.usId);
        assertEquals(task.title, inserted.title);
        assertEquals(task.duration, inserted.duration);
        assertEquals(task.status, inserted.status);
    }
}