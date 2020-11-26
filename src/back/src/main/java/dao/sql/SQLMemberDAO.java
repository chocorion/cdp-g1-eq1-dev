package dao.sql;

import dao.MemberDAO;
import domain.Member;
import domain.Task;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLMemberDAO extends SQLDAO<Member> implements MemberDAO {

    @Override
    protected Member createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Member(
            getInteger(resultSet, "project"),
            resultSet.getString("name"),
            resultSet.getString("role"),
            resultSet.getString("level"),
            getInteger(resultSet, "user"));
    }

    @Override
    public Member getById(int projectId, int user) throws SQLException {
        String statement = "SELECT * FROM member WHERE project=? AND user=?";
        List<Object> opt = Arrays.asList(projectId, user);

        return queryFirstObject(statement, opt);
    }


    @Override
    public List<Member> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM member WHERE project=?";

        List<Object> opt = Arrays.asList(projectId);
        return queryAllObjects(statement, opt);
    }


    @Override
    public Member insert(Member member) throws SQLException {
        if (member.user != null)
            throw new SQLException("This member already has a user, use update !");

        String statement = "INSERT INTO member (`project`, `name`, `role`, `level`) VALUES (?, ?, ?, ?)";
        List<Object> opt = Arrays.asList(member.project, member.name, member.role, member.level);

        return new Member(member.project, member.name, member.role, member.level, doInsert(statement, opt));
    }

    @Override
    public void update(Member member) throws SQLException {
        if (member.user == null)
            throw new SQLException("This member doesn't have a user, use insertOne");

        String statement = "UPDATE member SET project=?, name=?, role=?, level=? WHERE user=? LIMIT 1";
        List<Object> opt = Arrays.asList(member.project, member.name, member.role, member.level, member.user);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(Member member) throws SQLException {
        if (member.user == null)
            throw new SQLException("This member doesn't have a user");

        String statement =  "UPDATE task SET member = null WHERE member = ? ";
        List<Object> opt = Arrays.asList(member.user);

        SQLDatabase.exec(statement, opt);
        
        statement = "DELETE FROM member WHERE user=? LIMIT 1";
        opt = Arrays.asList(member.user);

        SQLDatabase.exec(statement, opt);
    }

}
