package dao.sql;

import dao.MemberDAO;
import domain.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLMemberDAO extends SQLDAO<Member> implements MemberDAO {

    @Override
    protected Member createObjectFromResult(ResultSet resultSet) throws SQLException{
        return new Member(
            resultSet.getInt("project"),
            resultSet.getString("role"),
            resultSet.getString("level"),
            resultSet.getInt("user"));
    }

    @Override
    public Member getById(int user) throws SQLException {
        String statement = "SELECT * FROM member WHERE user=?";
        List<Object> opt = Arrays.asList(user);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<Member> getAll() throws SQLException {
        String statement = "SELECT * FROM member";

        return queryAllObjects(statement);
    }

    @Override
    public Member insert(Member member) throws SQLException {
        if(member.user != null)
            throw new SQLException("This member already has a user, use update !");
        
        String statement = "INSERT INTO member (`project`, `role`, `level`) VALUES (?, ?, ?)";
        List<Object> opt = Arrays.asList(member.project, member.role, member.level);

        return new Member(member.project, member.role, member.level, doInsert(statement, opt));
    }

    @Override
    public void update(Member member) throws SQLException {
        if(member.user == null)
            throw new SQLException("This member doesn't have a user, use insertOne");
        
        String statement = "UPDATE member SET project=?, role=?, level=? WHERE user=? LIMIT 1";
        List<Object> opt = Arrays.asList(member.project, member.role, member.level, member.user);

        SQLDatabase.exec(statement,opt);
    }

    @Override
    public void delete(Member member) throws SQLException {
        if(member.user == null)
            throw new SQLException("This member doesn't have a user");
        
        String statement = "DELETE FROM member WHERE user=? LIMIT 1";
        List<Object> opt = Arrays.asList(member.user);

        SQLDatabase.exec(statement, opt);
    }

}