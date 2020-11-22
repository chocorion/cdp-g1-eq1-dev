package dao;

import domain.DOD;
import java.util.List;

public interface DODDAO {
    DOD getById(int id) throws Exception;
    List<DOD> getAllForProject(int project_id) throws Exception;
    List<DOD> getAllForTask(int project_id, int task_id) throws Exception;
    DOD insert(DOD dod) throws Exception;
    void update(DOD dod) throws Exception;
    void delete(DOD dod) throws Exception;
}
