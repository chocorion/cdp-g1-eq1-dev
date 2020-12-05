package dao.sql;

import org.junit.jupiter.api.Test;

import domain.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SQLReleaseDAOTest {
    @Test
    void testSimpleInsertDelete() {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04", new ArrayList<UserStory>());

        assertDoesNotThrow(() -> releaseDAO.delete(releaseDAO.insert(release)));
    }

    @Test
    void testSimpleInsertTwice() throws SQLException {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04", new ArrayList<UserStory>());

        Release inserted = assertDoesNotThrow(() -> releaseDAO.insert(release));
        releaseDAO.delete(assertDoesNotThrow(() -> releaseDAO.insert(release)));
        releaseDAO.delete(inserted);
    }

    @Test
    void testInsert() throws SQLException {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04", new ArrayList<UserStory>());

        Release insertedRelease = releaseDAO.insert(release);

        assertEquals(release.title, insertedRelease.title);
        assertEquals(release.description, insertedRelease.description);
        assertEquals(release.version, insertedRelease.version);
        assertEquals(release.link, insertedRelease.link);
        assertEquals(release.creationDate, insertedRelease.creationDate);
        assertEquals(release.project, insertedRelease.project);
    }

    @Test
    void testUpdate() throws Exception {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();

        SQLUserStoryDAO userStoryDAO = new SQLUserStoryDAO();
        UserStory us = new UserStory(1, "US trop bien", "High", 2, null);
        UserStory insertedUs = userStoryDAO.insert(us);

        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04", Arrays.asList(insertedUs));

        Release insertedRelease = releaseDAO.insert(release);

        Release updateRelease = new Release(1, "test2", "desc2", new Version(12, 22, 32), "testest2", "2020-12-05", new ArrayList<UserStory>(), insertedRelease.id);
        assertDoesNotThrow(() -> releaseDAO.update(updateRelease));

        Release updatedRelease = releaseDAO.getById(updateRelease.id);

        assertTrue(updatedRelease.equals(updateRelease));
        assertTrue(updatedRelease.userStories.isEmpty());

        Release updateRelease2 = new Release(1, "test2", "desc2", new Version(12, 22, 32), "testest2", "2020-12-05", Arrays.asList(insertedUs), insertedRelease.id);
        assertDoesNotThrow(() -> releaseDAO.update(updateRelease2));

        Release updatedRelease2 = releaseDAO.getById(updateRelease2.id);
        assertTrue(updatedRelease2.equals(updateRelease2));
        assertTrue(updatedRelease2.userStories.get(0).equals(insertedUs));

        assertDoesNotThrow(() -> releaseDAO.delete(updatedRelease2));
        assertDoesNotThrow(() -> userStoryDAO.delete(insertedUs));
    }
}