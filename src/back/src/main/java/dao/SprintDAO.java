package dao;

import domain.Sprint;

import java.sql.SQLException;
import java.util.List;

public interface SprintDAO {
    Sprint getById(int projectId, int id) throws Exception;
    List<Sprint> getAllForProject(int projectId) throws Exception;
    Sprint getActiveForProject(int projectId) throws SQLException;
    Sprint insert(Sprint sprint) throws Exception;
    void update(Sprint sprint) throws Exception;
    void delete(Sprint sprint) throws Exception;
}
