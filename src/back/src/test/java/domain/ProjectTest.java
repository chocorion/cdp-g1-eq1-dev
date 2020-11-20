package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    @Test
    void testEqual() {
        Project p1 = new Project();
        Project p2 = new Project("p1", "d1");
        Project p3 = new Project("p1", "d1");
        Project p4 = new Project("p1", "d1", 1);
        Project p5 = new Project("p1", "d1", 2);
        Project p6 = new Project("p1", "d1", 2);

        assertNotEquals(p1, p2);
        assertEquals(p1, p1);
        assertEquals(p2, p3);
        assertNotEquals(p4, p3);
        assertNotEquals(p4, p5);
        assertEquals(p6, p5);

    }
}