package dao;

import domain.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Test> getById(int id) {
        Connection conn = SQLDAOFactory.getConnection();

        String statement = "SELECT * FROM tests WHERE id=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //Project project = new Project(resultSet.getString("name"), resultSet.getString("description"), id);
                Test test = new Test(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("lastExecution"),
                        resultSet.getString("state"),
                        id,
                        resultSet.getInt("project_id")
                );

                resultSet.close();
                preparedStatement.close();

                return Optional.of(test);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Test> getAllForProject(int projectId) {
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "SELECT * FROM tests WHERE project_id=?";
        List<Test> tests = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tests.add(
                        new Test(
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getDate("lastExecution"),
                                resultSet.getString("state"),
                                resultSet.getInt("id"),
                                projectId
                        )
                );
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tests;
    }

    @Override
    public Optional<Test> addOne(Test test) throws SQLException {
        if (test.getId().isPresent())
            throw new SQLException("This test already has an id, use update !");

        Connection conn = SQLDAOFactory.getConnection();
        String statement = "INSERT INTO tests (name, description, lastExecution, state, project_id) VALUE (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getDescription());
            if (test.getLastExecution() != null)
                preparedStatement.setDate(3, java.sql.Date.valueOf(String.valueOf(test.getLastExecution())));
            else
                preparedStatement.setDate(3, null);

            preparedStatement.setString(4, test.getState());
            preparedStatement.setInt(5, test.getProjectId());

            preparedStatement.execute();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next())
                return Optional.of(
                        new Test(
                                test.getName(),
                                test.getDescription(),
                                test.getLastExecution(),
                                test.getState(),
                                generatedKey.getInt(1),
                                test.getProjectId()
                        )
                );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void updateOne(Test test) throws SQLException {
        if (!test.getId().isPresent()) {
            throw new SQLException("This test doesn't has an id, use insertOne !");
        }
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "UPDATE tests SET name=?, description=?, state=? WHERE id=? LIMIT 1";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getDescription());
            preparedStatement.setString(3, test.getState());
            preparedStatement.setInt(4, test.getId().get());

            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
