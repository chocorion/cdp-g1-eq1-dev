package dao;

import domain.UserStory;
import java.util.List;

public interface UserStoryDAO {
    UserStory getById(int projectId, int id) throws Exception;
    List<UserStory> getBySprint(int projectId, int sprintId) throws Exception;
    List<UserStory> getAllForProject(int projectId) throws Exception;
    List<UserStory> getUnreleasedForProject(int projectId) throws Exception;
    UserStory insert(UserStory userStory) throws Exception;
    void update(UserStory userStory) throws Exception;
    void delete(UserStory userStory) throws Exception;

    List<UserStory> getByPriority(int projectId, String priority) throws Exception;
    List<UserStory> getByDifficulty(int projectId, int difficulty) throws Exception;
}
