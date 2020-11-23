package dao.sql;

import org.junit.jupiter.api.Test;

import domain.Member;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SQLMemberDAOTest {

    @Test 
    void testSimpleInsertDelete(){
        SQLMemberDAO memberDAO = new SQLMemberDAO();
        Member member = new Member(1,"developper","senior");

        assertDoesNotThrow(() -> memberDAO.delete(memberDAO.insert(member)));
    }

    @Test 
    void testSimpleInsertTwice() {
        SQLMemberDAO memberDAO = new SQLMemberDAO();
        Member member = new Member(1,"product owner","junior");

        Member insertedMember = (Member) assertDoesNotThrow(() -> memberDAO.insert(member));
        assertThrows(SQLException.class, () -> memberDAO.insert(insertedMember));
    }

    @Test 
    void testGetNotThrow() {
        assertDoesNotThrow(() -> new SQLMemberDAO().getAllForProject(1));
    }


    @Test
    void testInsert() throws SQLException {
        SQLMemberDAO memberDAO = new SQLMemberDAO();
        Member member = new Member(1,"product owner","junior");

        Member insertedMember = memberDAO.insert(member);
        assertEquals(member.project , insertedMember.project);
        assertEquals(member.role , insertedMember.role);
        assertEquals(member.level , insertedMember.level);


        try {
            memberDAO.delete(insertedMember);
        } catch(Exception exception) {
            // We don't want this to fail the test
            // It is to be able to launch the tests twice in a row
        }
    }
}