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
        Connection conn = SQLDAOFactory.getConnection();

        String statement = "SELECT * FROM tests WHERE id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

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
            preparedStatement.close();

            return test;
        }

        throw new SQLException("Can't find test with this id");
    }

    @Override
    public List<Test> getAllForProject(int projectId) throws SQLException {
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "SELECT * FROM tests WHERE project_id=?";
        List<Test> tests = new ArrayList<>();

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setInt(1, projectId);
        ResultSet resultSet = preparedStatement.executeQuery();

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
        preparedStatement.close();

        return tests;
    }

    @Override
    public Test addOne(Test test) throws SQLException {
        if (test.id != null)
            throw new SQLException("This test already has an id, use update !");

        Connection conn = SQLDAOFactory.getConnection();
        String statement = "INSERT INTO tests (name, description, lastExecution, state, project_id) VALUE (?, ?, ?, ?, ?)";


        PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, test.name);
        preparedStatement.setString(2, test.description);
        if (test.lastExecution != null)
            preparedStatement.setDate(3, java.sql.Date.valueOf(String.valueOf(test.lastExecution)));
        else
            preparedStatement.setDate(3, null);

        preparedStatement.setString(4, test.state);
        preparedStatement.setInt(5, test.projectId);

        preparedStatement.execute();

        ResultSet generatedKey = preparedStatement.getGeneratedKeys();
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
    public void updateOne(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't has an id, use insertOne !");
        }
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "UPDATE tests SET name=?, description=?, state=?, lastExecution=? WHERE id=? LIMIT 1";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, test.name);
            preparedStatement.setString(2, test.description);
            preparedStatement.setString(3, test.state);

            if (test.lastExecution != null) {
                preparedStatement.setDate(4, java.sql.Date.valueOf(String.valueOf(test.lastExecution)));
            } else {
                preparedStatement.setDate(4, null);
            }

            preparedStatement.setInt(5, test.id);
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void deleteOne(Test test) throws SQLException {
        if (test.id == null) {
            throw new SQLException("This test doesn't has an id !");
        }

        Connection conn = SQLDAOFactory.getConnection();
        String statement = "DELETE FROM tests WHERE id=? LIMIT 1";

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setInt(1, test.id);

        preparedStatement.execute();
    }
}
