package dao;

import domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Project> getById(int id) {
        Connection conn = SQLDAOFactory.getConnection();

        String statement = "SELECT * FROM projects WHERE id=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Project project = new Project(resultSet.getString("name"), resultSet.getString("description"), id);

                resultSet.close();
                preparedStatement.close();

                return Optional.of(project);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Project> getAll() {
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();

        try {
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
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return projects;
    }

    @Override
    public Optional<Project> addOne(Project project) throws SQLException {
        if (project.getId().isPresent())
            throw new SQLException("This project already has an id, use update !");

        Connection conn = SQLDAOFactory.getConnection();
        String statement = "INSERT INTO projects (name, description) VALUE (?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());

            preparedStatement.execute();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next())
                return Optional.of(new Project(project.getName(), project.getDescription(), generatedKey.getInt(1)));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void updateOne(Project project) throws SQLException {
        if (!project.getId().isPresent()) {
            throw new SQLException("This project doesn't has an id, use insertOne !");
        }
        Connection conn = SQLDAOFactory.getConnection();
        String statement = "UPDATE projects SET name=?, description=? WHERE id=? LIMIT 1";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setInt(3, project.getId().get());

            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
