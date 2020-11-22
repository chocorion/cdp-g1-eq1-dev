package dao.sql;

import dao.UserStoryDAO;
import domain.UserStory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLUserStoryDAO extends SQLDAO<UserStory> implements UserStoryDAO {

    @Override
    protected UserStory createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserStory(
            resultSet.getInt("project"),
            resultSet.getString("description"),
            resultSet.getString("priority"),
            resultSet.getInt("difficulty"),
            resultSet.getInt("id")
        );
    }

    @Override
    public UserStory getById(int project_id, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id=?";

        List<Object> opt = Arrays.asList(project_id, id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public UserStory getBySprint(int project_id, int sprintId) throws Exception {
        String statement = "SELECT * FROM us WHERE project=? AND sprint=?";

        List<Object> opt = Arrays.asList(project_id, sprintId);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<UserStory> getAllForProject(int project_id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=?";

        List<Object> opt = Arrays.asList(project_id);

        return queryAllObjects(statement, opt);
    }

    @Override
    public UserStory insert(UserStory us) throws SQLException {
        String statement = "{CALL insert_us(?, ?, ?, ?, @id)}";

        List<Object> opt = Arrays.asList(
                us.projectId,
                us.description,
                us.priority,
                us.difficulty
        );

        return new UserStory(us.projectId, us.description, us.priority, us.difficulty, doInsert(statement, opt));
    }

    @Override
    public void update(UserStory us) throws SQLException {
        String statement = "UPDATE us SET description = ?, priority = ?, difficulty = ? WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(
                us.description,
                us.priority,
                us.difficulty,
                us.projectId,
                us.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(UserStory us) throws SQLException {
        String statement = "DELETE FROM us WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(us.projectId, us.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public List<UserStory> getByPriority(int project_id, String priority) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND priority = ?";

        List<Object> opt = Arrays.asList(project_id, priority);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<UserStory> getByDifficulty(int project_id, int difficulty) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND difficulty = ?";

        List<Object> opt = Arrays.asList(project_id, difficulty);

        return queryAllObjects(statement, opt);
    }
}
