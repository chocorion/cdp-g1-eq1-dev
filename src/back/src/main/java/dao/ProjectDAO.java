package dao;

import domain.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectDAO {
    Optional<Project> getById(int id);
    List<Project> getAll();
    Project addOne(Project project) throws SQLException;
    void updateOne(Project project) throws SQLException;
    void deleteOne(Project project) throws SQLException;
}
