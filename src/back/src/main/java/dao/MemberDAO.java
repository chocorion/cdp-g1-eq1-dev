package dao;

import domain.Member;
import java.util.List;

public interface MemberDAO {
    Member getById(int project_id, int user) throws Exception;
    List<Member> getAllForProject(int project_id) throws Exception;
    Member insert(Member member) throws Exception;
    void update(Member member) throws Exception;
    void delete(Member member) throws Exception;
}
