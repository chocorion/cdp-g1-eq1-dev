package domain;

import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test 
    void testEqual() {
        Member m1 = new Member();
        Member m2 = new Member(1, "product owner", "senior");
        Member m3 = new Member(1, "product owner", "senior");
        Member m4 = new Member(1, "product owner", "senior",3);
        Member m5 = new Member(1, "developper", "junior", 3);
        Member m6 = new Member(2, "developper","junior",4);

        assertNotEquals(m1,m2);
        assertEquals(m2,m3);
        assertNotEquals(m4,m5);
        assertNotEquals(m5,m6);
        assertEquals(m1,m1);
    }
}