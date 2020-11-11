package dao;

import domain.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TestDAO {
    Optional<Test> getById(int id);
    List<Test> getAllForProject(int projectId);
    Optional<Test> addOne(Test test) throws SQLException;
    void updateOne(Test test) throws SQLException;
}
