package dao;

import domain.Member;
import java.util.List;

public interface MemberDAO {
    Member getById(int id) throws Exception;
    List<Member> getAll() throws Exception;
    Member insert(Member member) throws Exception;
    void update(Member member) throws Exception;
    void delete(Member member) throws Exception;
}
