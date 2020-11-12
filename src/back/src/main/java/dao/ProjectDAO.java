package dao;

import domain.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO {
    Project getById(int id) throws SQLException;
    List<Project> getAll() throws SQLException;
    Project addOne(Project project) throws SQLException;
    void updateOne(Project project) throws SQLException;
    void deleteOne(Project project) throws SQLException;
}
