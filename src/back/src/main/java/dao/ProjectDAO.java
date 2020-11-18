package dao;

import domain.Project;
import java.util.List;

public interface ProjectDAO {
    Project getById(int id) throws Exception;
    List<Project> getAll() throws Exception;
    Project addOne(Project project) throws Exception;
    void updateOne(Project project) throws Exception;
    void deleteOne(Project project) throws Exception;
}
