package dao;

import domain.Member;
import java.util.List;

public interface MemberDAO {
    Member getById(int projectId, int user) throws Exception;
    List<Member> getAllForProject(int projectId) throws Exception;
    Member insert(Member member) throws Exception;
    void update(Member member) throws Exception;
    void delete(Member member) throws Exception;
}
