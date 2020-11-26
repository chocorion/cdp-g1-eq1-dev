package dao.sql;

import dao.DODDAO;
import domain.DOD;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLDODDAO extends SQLDAO<DOD> implements DODDAO {

    @Override
    protected DOD createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new DOD(
            getInteger(resultSet, "project"),
            getInteger(resultSet, "task"),
            resultSet.getString("description"),
            resultSet.getBoolean("state"),
            getInteger(resultSet, "id")
        );
    }

    @Override
    public DOD getById(int id) throws SQLException {
        String statement = "SELECT * FROM dod WHERE id=?";

        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<DOD> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM dod WHERE project=?";

        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<DOD> getAllForTask(int projectId, int taskId) throws SQLException {
        String statement = "SELECT * FROM dod WHERE project=? AND task=?";

        List<Object> opt = Arrays.asList(projectId, taskId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public DOD insert(DOD dod) throws SQLException {
        if (dod.id != null)
            throw new SQLException("This DOD already has an id, cannot insert");

            String statement = "INSERT INTO dod (project, task, description, state) VALUES (?,?,?,?)";

        List<Object> opt = Arrays.asList(
            dod.projectId,
            dod.taskId,
            dod.description,
            dod.state
        );

        return new DOD(dod.projectId, dod.taskId, dod.description, dod.state, doInsert(statement, opt));
    }

    @Override
    public void update(DOD dod) throws SQLException {
        if (dod.id == null)
            throw new SQLException("This sprint doesn't have an id");

        String statement = "UPDATE dod SET description = ?, state = ? WHERE id = ?";

        List<Object> opt = Arrays.asList(
            dod.description,
            dod.state,
            dod.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(DOD dod) throws SQLException {
        if (dod.id == null)
            throw new SQLException("This sprint doesn't have an id, cannot delete");

        String statement = "DELETE FROM dod WHERE id = ?";

        List<Object> opt = Arrays.asList(dod.id);

        SQLDatabase.exec(statement, opt);
    }
}
