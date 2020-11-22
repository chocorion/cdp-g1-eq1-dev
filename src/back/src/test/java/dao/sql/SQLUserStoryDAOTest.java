package dao.sql;

import domain.UserStory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserStoryDAOTest {
    @Test
    void testSimpleInsert() {
        SQLUserStoryDAO userStoryDAO = new SQLUserStoryDAO();
        UserStory us = new UserStory(1, "US trop bien", "High", 2, null);

        assertDoesNotThrow(() -> userStoryDAO.insert(us));
    }

    @Test
    void testGetNotThrow()  {
        assertDoesNotThrow(() -> new SQLUserStoryDAO().getAllForProject(1));
    }

    @Test
    void testSimpleInsertTwice() {
        SQLUserStoryDAO userStoryDAO = new SQLUserStoryDAO();
        UserStory us = new UserStory(1, "US trop bien", "High", 2, null);

        assertDoesNotThrow(() -> userStoryDAO.insert(us));
        assertDoesNotThrow(() -> userStoryDAO.insert(us));
    }

    @Test
    void testInsert() throws Exception {
        SQLUserStoryDAO userStoryDAO = new SQLUserStoryDAO();
        UserStory us = new UserStory(1, "US trop bien", "High", 2, null);

        UserStory inserted = userStoryDAO.insert(us);

        assertFalse(us.equals(inserted));
        assertEquals(us.description, inserted.description);
        assertEquals(us.projectId, inserted.projectId);
        assertEquals(us.difficulty, inserted.difficulty);
        assertEquals(us.priority, inserted.priority);
        assertEquals(us.sprint, inserted.sprint);
    }
}