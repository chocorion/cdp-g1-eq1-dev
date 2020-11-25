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
            resultSet.getInt("project"),
            resultSet.getInt("us"),
            resultSet.getInt("member"),
            resultSet.getString("title"),
            resultSet.getString("duration"),
            resultSet.getString("status"),
            resultSet.getInt("id")
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
    public List<Task> getParentTasks(Task task) throws SQLException {
        if (task.id == null)
            throw new SQLException("This task doesn't have an id, cannot select");

        String statement = "SELECT * FROM task_dep as td, task as t WHERE td.project = t.project AND t.project = ? AND td.parent = t.id AND td.child = ?";
        List<Object> opt = Arrays.asList(task.projectId, task.id);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Task> getChildrenTasks(Task task) throws SQLException {
        if (task.id == null)
            throw new SQLException("This task doesn't have an id, cannot select");

        String statement = "SELECT * FROM task_dep as td, task as t WHERE td.project = t.project AND t.project = ? AND td.child = t.id AND td.parent = ?";
        List<Object> opt = Arrays.asList(task.projectId, task.id);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Task> getAllForUserStory(int projectId, int usId) throws SQLException {

        String statement = "SELECT * FROM task WHERE project=? AND us=?";
        List<Object> opt = Arrays.asList(projectId, usId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Task> getAllForMember(int projectId, int memberId) throws SQLException {

        String statement = "SELECT * FROM task WHERE project=? AND member=?";
        List<Object> opt = Arrays.asList(projectId, memberId);

        return queryAllObjects(statement, opt);
    }


    @Override
    public Task insert(Task task) throws Exception {
        if (task.id != null)
            throw new SQLException("This task has an id, cannot insert");

        String statement = "{CALL insert_task(?, ?, ?, ?, ?, ?, @id)}";

        List<Object> opt = Arrays.asList(
                task.projectId,
                task.title,
                task.duration,
                task.status,
                task.usId,
                task.memberId
        );

        return new Task(task.projectId, task.usId, task.memberId, task.title, task.duration, task.status, doInsert(statement, opt));
    }

    @Override 
    public void addDependency(Task parent, Task child) throws SQLException {
        if (parent.id == null)
            throw new SQLException("The parent task is missing an id, cannot add dependency");
        if (parent.id == null)
            throw new SQLException("The child task is missing an id, cannot add dependency");
        if (parent.projectId != child.projectId)
            throw new SQLException("The project id does not match for these tasks, cannot add dependency");

        List<Task> children = this.getChildrenTasks(child);

        for(int i = 0; i < children.size(); i++)
            if(children.get(i).id == parent.id)
                throw new SQLException("Cannot set a parent for a child that is already its parent");

        String statement = "INSERT INTO task_dep (project, parent, child) VALUES (?,?,?)";

        List<Object> opt = Arrays.asList(
            parent.projectId,
            parent.id,
            child.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void update(Task task) throws SQLException {
        if (task.id == null)
            throw new SQLException("This task doesn't have an id, cannot update");

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
        if (task.id == null)
            throw new SQLException("This task doesn't have an id, cannot delete");

        String statement = "DELETE FROM us WHERE project = ? AND id = ?";
        List<Object> opt = Arrays.asList(task.projectId, task.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void deleteDependency(Task parent, Task child) throws SQLException {
        if (parent.id == null)
            throw new SQLException("The parent doesn't have an id, cannot delete");
        if (child.id == null)
            throw new SQLException("The child doesn't have an id, cannot delete");

        String statement = "DELETE FROM task_dep WHERE project = ? AND parent = ? AND child = ?";
        List<Object> opt = Arrays.asList(parent.projectId, parent.id, child.id);

        SQLDatabase.exec(statement, opt);
    }
}
