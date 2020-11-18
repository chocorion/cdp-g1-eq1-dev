package dao;

import domain.Test;
import java.util.List;

public interface TestDAO {
    Test getById(int id) throws Exception;
    List<Test> getAllForProject(int projectId) throws Exception;
    Test addOne(Test test) throws Exception;
    void updateOne(Test test) throws Exception;
    void deleteOne(Test test) throws Exception;
}
