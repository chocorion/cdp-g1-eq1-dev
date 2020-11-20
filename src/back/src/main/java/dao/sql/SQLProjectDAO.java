package dao.sql;

import dao.ProjectDAO;
import domain.Project;

import java.sql.*;
import java.util.ArrayList;
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

        List<Object> opt = new ArrayList<>();
        opt.add(id);

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

        String statement = "INSERT INTO project (name, description) VALUE (?, ?)";

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
