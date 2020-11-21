package dao.sql;

import dao.TaskDAO;
import domain.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SQLTaskDAO extends SQLDAO<Task> implements TaskDAO {
    @Override
    protected Task createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getInt("id"),
                resultSet.getInt("project"),
                resultSet.getInt("us"),
                resultSet.getString("title"),
                resultSet.getString("duration"),
                resultSet.getString("status")
        );
    }

    @Override
    public Task getById(int projectId, int id) throws SQLException {
        String statement = "SELECT * FROM task WHERE project=? AND id=?";
        List<Object> opt = Arrays.asList(projectId, id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Task> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM task WHERE project=?";
        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Task> getAllForUserStory(int projectId, int usId) throws SQLException {
        String statement = "SELECT * FROM task WHERE project=? AND us=?";
        List<Object> opt = Arrays.asList(projectId, usId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Task insert(Task task) throws Exception {
        String statement = "INSERT INTO task (project, title, duration, status, us) ";
        statement += "VALUES (?,?,?,?,?)";

        List<Object> opt = Arrays.asList(
                task.projectId,
                task.title,
                task.duration,
                task.status,
                task.usId
        );

        return new Task(doInsert(statement, opt), task.projectId, task.usId, task.title, task.duration, task.status);
    }

    @Override
    public void update(Task task) throws SQLException {
        String statement = "UPDATE task SET title = ?, duration = ?, status = ?, us = ? WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(
                task.title,
                task.duration,
                task.status,
                task.usId,
                task.projectId,
                task.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Task task) throws SQLException {
        String statement = "DELETE FROM us WHERE project = ? AND id = ?";
        List<Object> opt = Arrays.asList(task.projectId, task.id);

        SQLDatabase.exec(statement, opt);
    }
}
