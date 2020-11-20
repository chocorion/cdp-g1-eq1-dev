package dao;

import domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLProjectDAO extends SQLDAO<Project> implements ProjectDAO {
    
    @Override
    protected Project getObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Project(
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getInt("id"));
    }

    @Override
    public Project getById(int id) throws SQLException {
        String statement = "SELECT * FROM projects WHERE id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        if (resultSet.next()) {
            Project project = getObjectFromResult(resultSet);

            resultSet.close();

            return project;
        }

        throw new SQLException("Can't find project with this id");
    }

    @Override
    public List<Project> getAll() throws SQLException {
        String statement = "SELECT * FROM projects";

        ResultSet resultSet = SQLDAOFactory.query(statement);

        return getAllObjectsFromResult(resultSet);
    }

    @Override
    public Project insert(Project project) throws SQLException {
        if (project.id != null)
            throw new SQLException("This project already has an id, use update !");

        String statement = "INSERT INTO projects (name, description) VALUE (?, ?)";

        List<Object> opt = new ArrayList<>();
        opt.add(project.name);
        opt.add(project.description);

        return doInsert(statement, opt);
    }

    @Override
    public void update(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id, use insertOne !");
        }

        String statement = "UPDATE projects SET name=?, description=? WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.name);
        opt.add(project.description);
        opt.add(project.id);

        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public void delete(Project project) throws SQLException {
        if (project.id == null) {
            throw new SQLException("This project doesn't has an id !");
        }
        String statement = "DELETE FROM projects WHERE id=? LIMIT 1";

        List<Object> opt = new ArrayList<>();
        opt.add(project.id);

        SQLDAOFactory.exec(statement, opt);
    }
}
