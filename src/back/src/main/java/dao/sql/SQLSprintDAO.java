package dao.sql;

import dao.SprintDAO;
import domain.Sprint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SQLSprintDAO extends SQLDAO<Sprint> implements SprintDAO {
    @Override
    protected Sprint createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Sprint(
            resultSet.getInt("project"),
            resultSet.getString("name"),
            resultSet.getInt("id")
        );
    }

    @Override
    public Sprint getById(int projectId, int id) throws SQLException {
        String statement = "SELECT * FROM sprint WHERE project=? AND id=?";
        List<Object> opt = Arrays.asList(projectId, id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Sprint> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM sprint WHERE project=?";
        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Sprint insert(Sprint sprint) throws Exception {
        if (sprint.id != null)
            throw new SQLException("This sprint already has an id, use update !");
        String statement = "{CALL insert_sprint(?, ?, @id)}";

        List<Object> opt = Arrays.asList(
                sprint.projectId,
                sprint.name
        );

        return new Sprint(sprint.projectId, sprint.name, doInsert(statement, opt));
    }

    @Override
    public void update(Sprint sprint) throws SQLException {
        String statement = "UPDATE sprint SET name = ? WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(
                sprint.name,
                sprint.projectId,
                sprint.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Sprint sprint) throws SQLException {
        String statement = "DELETE FROM sprint WHERE project = ? AND id = ?";
        List<Object> opt = Arrays.asList(sprint.projectId, sprint.id);

        SQLDatabase.exec(statement, opt);
    }
}
