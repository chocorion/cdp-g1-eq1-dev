package dao.sql;

import dao.ReleaseDAO;
import domain.Release;
import domain.UserStory;
import domain.Version;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLReleaseDAO extends SQLDAO<Release> implements ReleaseDAO {

    @Override
    protected Release createObjectFromResult(ResultSet resultSet) throws SQLException {
        int id = getInteger(resultSet, "id");
        int projectId = getInteger(resultSet, "project");

        return new Release(
            projectId,
            resultSet.getString("title"),
            resultSet.getString("description"),
            new Version(getInteger(resultSet, "version_major"),
                        getInteger(resultSet, "version_minor"),
                        getInteger(resultSet, "version_patch")),
            resultSet.getString("link"),
            resultSet.getString("creation_date"),
            getUserStories(projectId, id),
            id);
    }

    @Override
    public Release getById(int id) throws SQLException {
        String statement = "SELECT * FROM `release` WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<UserStory> getUserStories(int projectId, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project = ? AND id IN (SELECT us FROM release_us WHERE `release`=? AND project=?)";
        List<Object> opt = Arrays.asList(projectId, id, projectId);

        return new SQLUserStoryDAO().queryAllObjects(statement, opt);
    }

    @Override
    public List<Release> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM `release` WHERE project=?";
        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Release insert(Release release) throws SQLException {
        if (release.id != null)
            throw new SQLException("This release already has an id, use update !");

        checkLatest(release);

        if (release.userStories != null) {
            for (UserStory us : release.userStories) {
                if (us.projectId != release.project) {
                    throw new SQLException("Invalid us in release : Different project");
                }
                if (us.id == null) {
                    throw new SQLException("Invalid us in release : No ID");
                } else {
                    UserStory usGet = new SQLUserStoryDAO().getById(us.projectId, us.id);
                    if (usGet == null) {
                        throw new SQLException("Invalid us in release : Doesn't exist");
                    }
                }
            }
        }

        String statement = "INSERT INTO `release` (`project`, `title`, `description`, `version_major`, `version_minor`, `version_patch`, `link`, `creation_date`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        List<Object> opt = Arrays.asList(
            release.project,
            release.title,
            release.description,
            release.version.versionMajor,
            release.version.versionMinor,
            release.version.versionPatch,
            release.link,
            release.creationDate
        );

        int id = doInsert(statement, opt);

        if (release.userStories != null) {
            for (UserStory us : release.userStories) {
                statement = "INSERT INTO release_us (project, `release`, us) VALUES (?,?,?)";
                opt = Arrays.asList(us.projectId, id, us.id);

                SQLDatabase.exec(statement, opt);
            }
        }

        return new Release(
            release.project,
            release.title,
            release.description,
            release.version,
            release.link,
            release.creationDate,
            getUserStories(release.project, id),
            id);
    }

    @Override
    public void update(Release release) throws SQLException {
        if (release.id == null) {
            throw new SQLException("This release doesn't have an id, use insert !");
        }

        checkLatest(release);

        for (UserStory us : release.userStories) {
            if (us.projectId != release.project) {
                throw new SQLException("Invalid us in release : Different project");
            }
            if (us.id == null) {
                throw new SQLException("Invalid us in release : No ID");
            } else {
                UserStory usGet = new SQLUserStoryDAO().getById(us.projectId, us.id);
                if (usGet == null) {
                    throw new SQLException("Invalid us in release : Doesn't exist");
                }
            }
        }
        String statement = "UPDATE `release` SET title=?, description=?, version_major=?, version_minor=?, version_patch=?, link=?, creation_date=? WHERE id=? LIMIT 1";
        List<Object> opt = Arrays.asList(
            release.title,
            release.description,
            release.version.versionMajor,
            release.version.versionMinor,
            release.version.versionPatch,
            release.link,
            release.creationDate,
            release.id
        );

        SQLDatabase.exec(statement, opt);

        statement = "DELETE FROM release_us WHERE `release` = ?";
        opt = Arrays.asList(release.id);

        SQLDatabase.exec(statement, opt);

        for (UserStory us : release.userStories) {
            statement = "INSERT INTO release_us (project, `release`, us) VALUES (?,?,?)";
            opt = Arrays.asList(us.projectId, release.id, us.id);

            SQLDatabase.exec(statement, opt);
        }
    }

    @Override
    public Release getProjectLatestRelease(int projectId) throws SQLException {
        String statement = "SELECT * FROM `release` WHERE project=? ORDER BY version_major DESC, version_minor DESC, version_patch DESC LIMIT 1";
        List<Object> opt = Arrays.asList(projectId);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void delete(Release release) throws SQLException {
        if (release.id == null) {
            throw new SQLException("This release doesn't have an id ! Cannot delete");
        }

        String statement = "DELETE FROM `release` WHERE id=? LIMIT 1";
        List<Object> opt = Arrays.asList(release.id);

        SQLDatabase.exec(statement, opt);
    }

    protected void checkLatest(Release release) throws SQLException {
        Release latest = null;
        try {
            latest = getProjectLatestRelease(release.project);
        } catch (SQLException exception) {
            return;
        }

        int cc = release.version.compareTo(latest.version);

        if (latest != null && cc <= 0) {
            throw new SQLException("The release's version must be greater than the latest one, " + release.version + " < " + latest.version);
        }
    }
}
