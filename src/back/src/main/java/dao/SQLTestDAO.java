package dao;

import domain.Test;

import java.sql.*;
import java.util.ArrayList;
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
    public Test getById(int id) throws SQLException {
        String statement = "SELECT * FROM test WHERE id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Test> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM test WHERE project=?";

        List<Object> opt = new ArrayList<>();
        opt.add(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Test insert(Test test) throws SQLException {
        if (test.id != null)
            throw new SQLException("This test already has an id, use update !");

        String statement = "INSERT INTO test (name, description, lastExecution, state, project) VALUE (?, ?, ?, ?, ?)";

        List<Object> opt = new ArrayList<>();
        opt.add(test.name);
        opt.add(test.description);

        if (test.lastExecution != null)
            opt.add(java.sql.Date.valueOf(String.valueOf(test.lastExecution)));
        else
            opt.add(null);

        opt.add(test.state);
        opt.add(test.projectId);

        return doInsert(statement, opt);
    }

    @Override
    public void update(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't have an id, use insertOne !");
        }
        String statement = "UPDATE test SET name=?, description=?, state=?, lastExecution=? WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(test.name);
        opt.add(test.description);
        opt.add(test.state);

        if (test.lastExecution != null) 
            opt.add(java.sql.Date.valueOf(String.valueOf(test.lastExecution)));
        else
            opt.add(null);

        opt.add(test.id);

        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public void delete(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't have an id !");
        }

        String statement = "DELETE FROM test WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(test.id);

        SQLDAOFactory.exec(statement, opt);
    }
}
