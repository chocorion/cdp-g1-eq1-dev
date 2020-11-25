package dao;

import domain.Task;

import java.util.List;

public interface TaskDAO {
    Task getById(int project_id, int id) throws Exception;
    List<Task> getAllForProject(int projectId) throws Exception;
    List<Task> getAllForUserStory(int projectId, int usId) throws Exception;
    List<Task> getAllForMember(int projectId, int memberId) throws Exception;

    void addDependency(Task parent, Task child) throws Exception;
    void deleteDependency(Task parent, Task child) throws Exception;
    List<Task> getParentTasks(Task task) throws Exception;
    List<Task> getChildrenTasks(Task task) throws Exception;

    Task insert(Task task) throws Exception;
    void update(Task task) throws Exception;
    void delete(Task task) throws Exception;
}
