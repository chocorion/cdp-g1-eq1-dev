package dao;

import domain.Test;
import java.util.List;

public interface TestDAO {
    Test getById(int projectId, int id) throws Exception;
    List<Test> getAllForProject(int projectId) throws Exception;
    Test insert(Test test) throws Exception;
    void update(Test test) throws Exception;
    void delete(Test test) throws Exception;
}
