package dao.SQLDAO;

import dao.ProjectDAO;
import domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLProjectDAO implements ProjectDAO {
    @Override
    public Project getById(int id) throws SQLException {
        String statement = "SELECT * FROM project WHERE id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(id);

        ResultSet resultSet = SQLDatabase.query(statement, opt);

        if (resultSet.next()) {
            Project project = new Project(resultSet.getString("name"), resultSet.getString("description"), id);

            resultSet.close();

            return project;
        }

        throw new SQLException("Can't find project with this id");
    }

    @Override
    public List<Project> getAll() throws SQLException {
        System.out.println("Coucou");
        String statement = "SELECT * FROM project";

        ResultSet resultSet = SQLDatabase.query(statement);

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

        System.out.println("End function");
        return projects;
    }

    @Override
    public Project insert(Project project) throws SQLException {
        if (project.id != null)
            throw new SQLException("This project already has an id, use update !");

        String statement = "INSERT INTO project (name, description) VALUE (?, ?)";

        List<Object> opt = new ArrayList<>();
        opt.add(project.name);
        opt.add(project.description);

        ResultSet generatedKey = SQLDatabase.exec(statement, opt);

        if (generatedKey.next())
            return new Project(project.name, project.description, generatedKey.getInt(1));

        throw new SQLException("Can't add this project in database");
    }

    @Override
    public void update(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id, use insertOne !");
        }

        String statement = "UPDATE project SET name=?, description=? WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.name);
        opt.add(project.description);
        opt.add(project.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id !");
        }
        String statement = "DELETE FROM project WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.id);

        SQLDatabase.exec(statement, opt);
    }
}
