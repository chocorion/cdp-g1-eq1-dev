package dao.sql;

import org.junit.jupiter.api.Test;

import domain.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLReleaseDAOTest {
    @Test
    void testSimpleInsertDelete() {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");

        assertDoesNotThrow(() -> releaseDAO.delete(releaseDAO.insert(release)));
    }

    @Test
    void testSimpleInsertTwice() throws SQLException {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");

        Release inserted = assertDoesNotThrow(() -> releaseDAO.insert(release));
        releaseDAO.delete(assertDoesNotThrow(() -> releaseDAO.insert(release)));
        releaseDAO.delete(inserted);
    }

    @Test
    void testInsert() throws SQLException {
        SQLReleaseDAO releaseDAO = new SQLReleaseDAO();
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");

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
        Release release = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");

        Release insertedRelease = releaseDAO.insert(release);

        Release updateRelease = new Release(1, "test2", "desc2", new Version(12, 22, 32), "testest2", "2020-12-05", insertedRelease.id);
        releaseDAO.update(updateRelease);

        Release updatedRelease = releaseDAO.getById(updateRelease.id);

        assertTrue(updatedRelease.equals(updateRelease));
        assertDoesNotThrow(() -> releaseDAO.delete(updatedRelease));
    }
}