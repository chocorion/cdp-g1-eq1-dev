package dao;

import domain.Release;
import java.util.List;

public interface ReleaseDAO {
    Release getById(int id) throws Exception;
    List<Release> getAllForProject(int projectId) throws Exception;
    Release insert(Release release) throws Exception;
    void update(Release release) throws Exception;
    void delete(Release release) throws Exception;
}
