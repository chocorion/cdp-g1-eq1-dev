package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SprintTest {
    @Test
    void testEqual() {
        Sprint s1 = new Sprint();
        Sprint s2 = new Sprint(1, "d1");
        Sprint s3 = new Sprint(1, "d1");
        Sprint s4 = new Sprint(2, "d1");

        assertNotEquals(s1, s2);
        assertEquals(s2, s3);
        assertNotEquals(s3, s4);


    }
}