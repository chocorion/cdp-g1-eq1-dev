package dao.sql;

import domain.Task;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLTaskDAOTest {
    @Test
    void testSimpleInsert() {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(1, 1, 1, "super title", "1h.h", "TODO");

        assertDoesNotThrow(() -> taskDAO.insert(task));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLProjectDAO().getAll());
    }

    @Test
    void testSimpleInsertTwice() {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(1, 1, 2, "super title2", "1h.h", "TODO");
        assertDoesNotThrow(() -> taskDAO.insert(task));
        assertDoesNotThrow(() -> taskDAO.insert(task));
    }

    @Test
    void testInsert() throws Exception {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        Task task = new Task(1, 1, 2, "super title3", "1h.h", "TODO");

        Task inserted = taskDAO.insert(task);

        assertNotEquals(task, inserted);
        assertEquals(task.projectId, inserted.projectId);
        assertEquals(task.usId, inserted.usId);
        assertEquals(task.memberId, inserted.memberId);
        assertEquals(task.title, inserted.title);
        assertEquals(task.duration, inserted.duration);
        assertEquals(task.status, inserted.status);
    }

    @Test
    void testDependency() throws Exception {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        
        Task task1 = taskDAO.insert(new Task(1, 1, 1, "super title", "1h.h", "TODO"));
        Task task2 = taskDAO.insert(new Task(1, 1, 1, "super title", "1h.h", "TODO"));

        assertDoesNotThrow(() -> taskDAO.addDependency(task1, task2));
        assertThrows(SQLException.class, () -> taskDAO.addDependency(task1, task2));
        assertDoesNotThrow(() -> taskDAO.deleteDependency(task1, task2));
    }

    @Test
    void testUpdate() throws Exception {
        SQLTaskDAO taskDAO = new SQLTaskDAO();
        
        Task inserted = taskDAO.insert(new Task(1, 1, 1, "super title", "1h.h", "TODO"));
        
        Task updateTask = new Task(inserted.projectId, inserted.usId, inserted.memberId, "i have a new title", "562hh", "DONE", inserted.id);
        taskDAO.update(updateTask);

        Task updatedTask = taskDAO.getById(updateTask.projectId, updateTask.id);

        assertTrue(updateTask.equals(updatedTask));
    }
}