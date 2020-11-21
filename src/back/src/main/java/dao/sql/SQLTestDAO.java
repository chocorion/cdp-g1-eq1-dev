package dao.sql;

import dao.TestDAO;
import domain.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLTestDAO extends SQLDAO<Test> implements TestDAO {
    
    @Override
    protected Test createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Test(
            resultSet.getString("name"),
            resultSet.getString("description"),
            (resultSet.getDate("lastExecution") == null) ? null : resultSet.getDate("lastExecution").toString(),
            resultSet.getString("state"),
            resultSet.getInt("project"),
            resultSet.getInt("id")
        );
    }

    @Override
    public Test getById(int projectId, int id) throws SQLException {
        String statement = "SELECT * FROM test WHERE id=? AND project=?";

        List<Object> opt = Arrays.asList(id, projectId);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Test> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM test WHERE project=?";

        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Test insert(Test test) throws SQLException {
        if (test.id != null)
            throw new SQLException("This test already has an id, use update !");

        String statement = "{CALL insert_test(?, ?, ?, ?, ?, @id)}";
        String lastExecution = test.lastExecution;

        List<Object> opt = Arrays.asList(
                test.name,
                test.description,
                (lastExecution != null)? java.sql.Date.valueOf(lastExecution):null,
                test.state,
                test.projectId
        );

        return new Test(test.name, test.description, test.lastExecution, test.state, test.projectId, doInsert(statement, opt));
    }

    @Override
    public void update(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't have an id, use insert !");
        }
        String statement = "UPDATE test SET name=?, description=?, state=?, lastExecution=? WHERE id=? AND project=? LIMIT 1";
        String lastExecution = test.lastExecution;

        List<Object> opt = Arrays.asList(
                test.name,
                test.description,
                test.state,
                (lastExecution != null)? java.sql.Date.valueOf(lastExecution) : null,
                test.id,
                test.projectId
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't have an id !");
        }

        String statement = "DELETE FROM test WHERE id=? AND project=? LIMIT 1";

        List<Object> opt = Arrays.asList(test.id, test.projectId);

        SQLDatabase.exec(statement, opt);
    }
}
