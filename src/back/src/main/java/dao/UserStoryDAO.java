package dao;

import domain.UserStory;
import java.util.List;

public interface UserStoryDAO {
    UserStory getById(int project_id, int id) throws Exception;
    List<UserStory> getBySprint(int project_id, int sprintId) throws Exception;
    List<UserStory> getAllForProject(int project_id) throws Exception;
    UserStory insert(UserStory userStory) throws Exception;
    void update(UserStory userStory) throws Exception;
    void delete(UserStory userStory) throws Exception;

    List<UserStory> getByPriority(int project_id, String priority) throws Exception;
    List<UserStory> getByDifficulty(int project_id, int difficulty) throws Exception;
}
