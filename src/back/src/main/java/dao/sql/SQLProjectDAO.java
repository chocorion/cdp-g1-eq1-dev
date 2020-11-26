package dao.sql;

import dao.ProjectDAO;
import domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLProjectDAO extends SQLDAO<Project> implements ProjectDAO {

    @Override
    protected Project createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("id"));
    }

    @Override
    public Project getById(int id) throws SQLException {
        String statement = "SELECT * FROM project WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Project> getAll() throws SQLException {
        String statement = "SELECT * FROM project";

        return queryAllObjects(statement);
    }

    @Override
    public Project insert(Project project) throws SQLException {
        if (project.id != null)
            throw new SQLException("This project already has an id, use update !");

        String statement = "INSERT INTO project (`name`, `description`) VALUES (?, ?)";
        List<Object> opt = Arrays.asList(project.name, project.description);

        return new Project(project.name, project.description, doInsert(statement, opt));
    }

    @Override
    public void update(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't have an id, use insertOne !");
        }

        String statement = "UPDATE project SET name=?, description=? WHERE id=? LIMIT 1";
        List<Object> opt = Arrays.asList(
            project.name,
            project.description,
            project.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't have an id !");
        }

        String statement = "DELETE FROM task WHERE project=?";
        List<Object> opt = Arrays.asList(project.id);

        SQLDatabase.exec(statement, opt);

        statement = "DELETE FROM us WHERE project=?";
        opt = Arrays.asList(project.id);

        SQLDatabase.exec(statement, opt);

        statement = "DELETE FROM project WHERE id=? LIMIT 1";
        opt = Arrays.asList(project.id);

        SQLDatabase.exec(statement, opt);
    }
}
