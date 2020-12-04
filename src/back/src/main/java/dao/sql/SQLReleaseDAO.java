package dao.sql;

import dao.ReleaseDAO;
import domain.Release;
import domain.Version;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLReleaseDAO extends SQLDAO<Release> implements ReleaseDAO {

    @Override
    protected Release createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Release(
            getInteger(resultSet, "project"),
            resultSet.getString("title"),
            resultSet.getString("description"),
            new Version(getInteger(resultSet, "version_major"),
                        getInteger(resultSet, "version_minor"),
                        getInteger(resultSet, "version_patch")),
            resultSet.getString("link"),
            resultSet.getString("creationDate"),
            getInteger(resultSet, "id"));
    }

    @Override
    public Release getById(int id) throws SQLException {
        String statement = "SELECT * FROM `release` WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
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
        return new Release(
            release.project,
            release.title,
            release.description,
            release.version,
            release.link,
            release.creationDate,
            doInsert(statement, opt));
    }

    @Override
    public void update(Release release) throws SQLException {
        if (release.id == null) {
            throw new SQLException("This release doesn't have an id, use insert !");
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
}
