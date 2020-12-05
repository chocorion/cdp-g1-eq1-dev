package dao.sql;

import dao.SprintDAO;
import domain.Sprint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLSprintDAO extends SQLDAO<Sprint> implements SprintDAO {
    @Override
    protected Sprint createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Sprint(
            getInteger(resultSet, "project"),
            resultSet.getString("name"),
            resultSet.getString("state"),
            getInteger(resultSet, "id")
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
    public Sprint getActiveForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM sprint WHERE project=? AND state=?";
        List<Object> opt = Arrays.asList(projectId, "active");

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement, opt);
        ResultSet resultSet = preparedStatement.executeQuery();

        Sprint result = null;

        if (resultSet.next()) {
            result = createObjectFromResult(resultSet);
        }

        resultSet.close();
        preparedStatement.close();

        return result;
    }

    @Override
    public Sprint insert(Sprint sprint) throws Exception {
        if (sprint.id != null)
            throw new SQLException("This sprint already has an id, use update !");
        String statement = "{CALL insert_sprint(?, ?, ?, @id)}";

        List<Object> opt = Arrays.asList(
                sprint.projectId,
                sprint.name,
                sprint.state
        );

        return new Sprint(sprint.projectId, sprint.name, sprint.state, doInsert(statement, opt));
    }

    @Override
    public void update(Sprint sprint) throws SQLException {
        String statement = "UPDATE sprint SET name = ?, state = ? WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(
                sprint.name,
                sprint.state,
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
