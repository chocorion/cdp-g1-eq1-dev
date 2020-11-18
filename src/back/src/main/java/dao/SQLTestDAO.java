package dao;

import domain.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLTestDAO implements TestDAO {
    private static SQLTestDAO instance;

    private SQLTestDAO() {

    }

    public static SQLTestDAO getInstance() {
        if (instance == null)
            instance = new SQLTestDAO();

        return instance;
    }

    @Override
    public Test getById(int id) throws SQLException {
        String statement = "SELECT * FROM tests WHERE id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        if (resultSet.next()) {

            Test test = new Test(
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    (resultSet.getDate("lastExecution") == null) ? null : resultSet.getDate("lastExecution").toString(),
                    resultSet.getString("state"),
                    id,
                    resultSet.getInt("project_id")
            );

            resultSet.close();

            return test;
        }

        throw new SQLException("Can't find test with this id");
    }

    @Override
    public List<Test> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM tests WHERE project_id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(projectId);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        List<Test> tests = new ArrayList<>();

        while (resultSet.next()) {
            tests.add(
                    new Test(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            (resultSet.getDate("lastExecution") == null) ? null : resultSet.getDate("lastExecution").toString(),
                            resultSet.getString("state"),
                            resultSet.getInt("id"),
                            projectId
                    )
            );
        }

        resultSet.close();

        return tests;
    }

    @Override
    public Test insert(Test test) throws SQLException {
        if (test.id != null)
            throw new SQLException("This test already has an id, use update !");

        String statement = "INSERT INTO tests (name, description, lastExecution, state, project_id) VALUE (?, ?, ?, ?, ?)";

        List<Object> opt = new ArrayList<>();
        opt.add(test.name);
        opt.add(test.description);

        if (test.lastExecution != null)
            opt.add(java.sql.Date.valueOf(String.valueOf(test.lastExecution)));
        else
            opt.add(null);

        opt.add(test.state);
        opt.add(test.projectId);

        ResultSet generatedKey = SQLDAOFactory.exec(statement, opt);

        if (generatedKey.next())
            return new Test(
                    test.name,
                    test.description,
                    test.lastExecution,
                    test.state,
                    generatedKey.getInt(1),
                    test.projectId
            );

        throw new SQLException("Can't add this test");
    }

    @Override
    public void update(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't have an id, use insertOne !");
        }
        String statement = "UPDATE tests SET name=?, description=?, state=?, lastExecution=? WHERE id=? LIMIT 1";

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

        String statement = "DELETE FROM tests WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(test.id);

        SQLDAOFactory.exec(statement, opt);
    }
}
