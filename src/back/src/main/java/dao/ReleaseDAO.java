package dao;

import domain.Release;
import domain.UserStory;

import java.util.List;

public interface ReleaseDAO {
    Release getById(int id) throws Exception;
    List<Release> getAllForProject(int projectId) throws Exception;
    List<UserStory> getUserStories(int id) throws Exception;
    Release insert(Release release) throws Exception;
    void update(Release release) throws Exception;
    void delete(Release release) throws Exception;
}
