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
        String statement = "SELECT * FROM projects WHERE id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        if (resultSet.next()) {
            Project project = new Project(resultSet.getString("name"), resultSet.getString("description"), id);

            resultSet.close();

            return project;
        }

        throw new SQLException("Can't find project with this id");
    }

    @Override
    public List<Project> getAll() throws SQLException {
        String statement = "SELECT * FROM projects";

        ResultSet resultSet = SQLDAOFactory.query(statement);

        List<Project> projects = new ArrayList<>();

        while (resultSet.next()) {
            projects.add(
                    new Project(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("id"))
            );
        }

        resultSet.close();

        return projects;
    }

    @Override
    public Project insert(Project project) throws SQLException {
        if (project.getId() != -1)
            throw new SQLException("This project already has an id, use update !");

        String statement = "INSERT INTO projects (name, description) VALUE (?, ?)";

        List<Object> opt = new ArrayList<>();
        opt.add(project.getName());
        opt.add(project.getDescription());

        ResultSet generatedKey = SQLDAOFactory.exec(statement, opt);

        if (generatedKey.next())
            return new Project(project.getName(), project.getDescription(), generatedKey.getInt(1));

        throw new SQLException("Can't add this project in database");
    }

    @Override
    public void update(Project project) throws SQLException {
        if (project.getId() == -1) {
            throw new SQLException("This project doesn't has an id, use insertOne !");
        }

        String statement = "UPDATE projects SET name=?, description=? WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.getName());
        opt.add(project.getDescription());
        opt.add(project.getId());

        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public void delete(Project project) throws SQLException {
        if (project.getId() == -1) {
            throw new SQLException("This project doesn't has an id !");
        }
        String statement = "DELETE FROM projects WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.getId());

        SQLDAOFactory.exec(statement, opt);
    }
}
