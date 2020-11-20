package dao;

import domain.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserStoryDAO implements UserStoryDAO {
    private static SQLUserStoryDAO instance;

    private SQLUserStoryDAO() {

    }

    public static SQLUserStoryDAO getInstance() {
        if (instance == null)
            instance = new SQLUserStoryDAO();

        return instance;
    }

    private UserStory getObjectFromResult(ResultSet resultSet) {
        return new UserStory(
            resultSet.getInt("project"),
            resultSet.getInt("id"),
            resultSet.getString("description"),
            resultSet.getString("priority"),
            resultSet.getString("difficulty")
        );
    }

    @Override
    public UserStory getById(int project_id, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        if (resultSet.next()) {

            UserStory us = getObjectFromResult(resultSet);

            resultSet.close();

            return us;
        }

        throw new SQLException("Can't find user story with this id and project");
    }

    @Override
    public List<UserStory> getAllForProject(int project_id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        List<UserStory> uss = new ArrayList<>();

        while (resultSet.next()) {
            uss.add(getObjectFromResult(resultSet));
        }

        resultSet.close();

        return uss;
    }

    @Override
    public UserStory insert(UserStory us) throws SQLException {
        String statement = "INSERT INTO us (project, description, priority, difficulty)";
        statement += "VALUES (?,?,?,?)";

        List<Object> opt = new ArrayList<>();
        opt.add(us.project);
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        ResultSet generatedKey = SQLDAOFactory.exec(statement, opt);

        if (generatedKey.next())
            return getObjectFromResult(generatedKey);

        throw new SQLException("Can't add this user story");
    }

    @Override
    public void update(UserStory us) throws SQLException {
        String statement = "UPDATE us SET description = ?, priority = ?, difficulty = ? WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        opt.add(us.project);
        opt.add(us.id);

        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public void delete(UserStory us) throws SQLException {
        String statement = "DELETE FROM us WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.project);
        opt.add(us.id);
        
        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public List<UserStory> getByPriority(int project_id, String priority) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND priority = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(priority);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        List<UserStory> uss = new ArrayList<>();

        while (resultSet.next()) {
            uss.add(getObjectFromResult(resultSet));
        }

        resultSet.close();

        return uss;
    }

    @Override
    public List<UserStory> getByDifficulty(int project_id, int difficulty) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND difficulty = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(difficulty);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        List<UserStory> uss = new ArrayList<>();

        while (resultSet.next()) {
            uss.add(getObjectFromResult(resultSet));
        }

        resultSet.close();

        return uss;
    }
}
