package dao;

import domain.Task;

import java.util.List;

public interface TaskDAO {
    Task getById(int project_id, int id) throws Exception;
    List<Task> getAllForProject(int projectId) throws Exception;
    List<Task> getAllForUserStory(int projectId, int usId) throws Exception;

    Task insert(Task task) throws Exception;
    void update(Task task) throws Exception;
    void delete(Task task) throws Exception;
}
