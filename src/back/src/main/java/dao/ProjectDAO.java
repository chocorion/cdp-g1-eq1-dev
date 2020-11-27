package dao;

import domain.Project;
import java.util.List;

public interface ProjectDAO {
    Project getById(int id) throws Exception;
    List<Project> getAll() throws Exception;
    Project insert(Project project) throws Exception;
    void update(Project project) throws Exception;
    void delete(Project project) throws Exception;
}
