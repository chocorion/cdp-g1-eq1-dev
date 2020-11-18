package dao;

import domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLProjectDAO implements ProjectDAO {
    private static SQLProjectDAO instance;

    private SQLProjectDAO() {
    }

    public static SQLProjectDAO getInstance() {
        if (instance == null)
            instance = new SQLProjectDAO();

        return instance;
    }

    @Override
    public Project getById(int id) throws SQLException {
        Connection conn = SQLDAOFactory.getConnection();

        String statement = "SELECT * FROM projects WHERE id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Project project = new Project(resultSet.getString("name"), resultSet.getString("description"), id);

            resultSet.close();
            preparedStatement.close();

            return project;
        }

        throw new SQLException("Can't find project with this id");
    }

    @Override
    public List<Project> getAll() throws SQLException {
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();


        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            projects.add(
                    new Project(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("id"))
            );
        }

        resultSet.close();
        preparedStatement.close();


        return projects;
    }

    @Override
    public Project addOne(Project project) throws SQLException {
        if (project.id != null)
            throw new SQLException("This project already has an id, use update !");

        Connection conn = SQLDAOFactory.getConnection();
        String statement = "INSERT INTO projects (name, description) VALUE (?, ?)";


        PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, project.name);
        preparedStatement.setString(2, project.description);

        preparedStatement.execute();

        ResultSet generatedKey = preparedStatement.getGeneratedKeys();
        if (generatedKey.next())
            return new Project(project.name, project.description, generatedKey.getInt(1));

        throw new SQLException("Can't add this project in database");
    }

    @Override
    public void updateOne(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id, use insertOne !");
        }
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "UPDATE projects SET name=?, description=? WHERE id=? LIMIT 1";

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setString(1, project.name);
        preparedStatement.setString(2, project.description);
        preparedStatement.setInt(3, project.id);

        preparedStatement.execute();
    }

    @Override
    public void deleteOne(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id !");
        }
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "DELETE FROM projects WHERE id=? LIMIT 1";

        PreparedStatement preparedStatement = conn.prepareStatement(statement);
        preparedStatement.setInt(1, project.id);

        preparedStatement.execute();
    }
}
