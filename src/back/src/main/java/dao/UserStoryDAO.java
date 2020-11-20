package dao;

import domain.UserStory;
import java.util.List;

public interface UserStoryDAO {
    UserStory getById(int project_id, int id) throws Exception;
    List<UserStory> getAllForProject(int project_id) throws Exception;
    UserStory insert(UserStory project) throws Exception;
    void update(UserStory project) throws Exception;
    void delete(UserStory project) throws Exception;

    List<UserStory> getByPriority(int project_id, String priority) throws Exception;
    List<UserStory> getByDifficulty(int project_id, int difficulty) throws Exception;
}
