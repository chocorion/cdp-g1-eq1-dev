package dao;

import domain.DOD;
import java.util.List;

public interface DODDAO {
    DOD getById(int id) throws Exception;
    List<DOD> getAllForProject(int projectId) throws Exception;
    List<DOD> getAllForTask(int projectId, int taskId) throws Exception;
    DOD insert(DOD dod) throws Exception;
    void update(DOD dod) throws Exception;
    void delete(DOD dod) throws Exception;
}
