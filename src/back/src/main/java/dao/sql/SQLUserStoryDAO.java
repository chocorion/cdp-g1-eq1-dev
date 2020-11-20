package dao.sql;

import dao.UserStoryDAO;
import domain.UserStory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserStoryDAO extends SQLDAO<UserStory> implements UserStoryDAO {
    
    @Override
    protected UserStory createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserStory(
            resultSet.getInt("project"),
            resultSet.getInt("id"),
            resultSet.getString("description"),
            resultSet.getString("priority"),
            resultSet.getInt("difficulty")
        );
    }

    @Override
    public UserStory getById(int project_id, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(id);
        
        return queryFirstObject(statement, opt);
    }

    @Override
    public List<UserStory> getAllForProject(int project_id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);

        return queryAllObjects(statement, opt);
    }

    @Override
    public UserStory insert(UserStory us) throws SQLException {
        String statement = "INSERT INTO us (project, description, priority, difficulty)";
        statement += "VALUES (?,?,?,?)";

        List<Object> opt = new ArrayList<>();
        opt.add(us.projectId);
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        return doInsert(statement, opt);
    }

    @Override
    public void update(UserStory us) throws SQLException {
        String statement = "UPDATE us SET description = ?, priority = ?, difficulty = ? WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        opt.add(us.projectId);
        opt.add(us.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(UserStory us) throws SQLException {
        String statement = "DELETE FROM us WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.projectId);
        opt.add(us.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public List<UserStory> getByPriority(int project_id, String priority) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND priority = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(priority);
        
        return queryAllObjects(statement, opt);
    }

    @Override
    public List<UserStory> getByDifficulty(int project_id, int difficulty) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND difficulty = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(difficulty);
        
        return queryAllObjects(statement, opt);
    }
}
