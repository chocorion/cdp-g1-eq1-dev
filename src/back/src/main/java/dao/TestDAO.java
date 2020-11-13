package dao;

import domain.Test;

import java.sql.SQLException;
import java.util.List;

public interface TestDAO {
    Test getById(int id) throws SQLException;
    List<Test> getAllForProject(int projectId) throws SQLException;
    Test addOne(Test test) throws SQLException;
    void updateOne(Test test) throws SQLException;
    void deleteOne(Test test) throws SQLException;
}
