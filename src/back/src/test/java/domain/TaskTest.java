package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {
    @Test
    void testEqual() {
        Task t1 = new Task();
        Task t2 = new Task(1, 1, 1, "super title2", "1h.h", "TODO");
        Task t3 = new Task(1, 1, 1, "super title2", "1h.h", "TODO");
        Task t4 = new Task(1, 1, 1, "super title3", "1h.h", "TODO");

        assertNotEquals(t1, t2);
        assertEquals(t2, t3);
        assertNotEquals(t3, t4);
    }
}