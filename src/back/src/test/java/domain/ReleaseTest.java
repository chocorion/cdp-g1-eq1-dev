package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Releaseest {
    @Test
    void testEqual() {
        Release r1 = new Release();
        Release r2 = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");
        Release r3 = new Release(1, "test", "desc", new Version(1, 2, 3), "testest", "2020-12-04");
        Release r4 = new Release(1, "test", "desc", new Version(1, 2, 2), "testest", "2020-12-04");

        assertNotEquals(r1, r2);
        assertEquals(r1, r1);
        assertEquals(r2, r3);
        assertNotEquals(r4, r3);
        assertNotEquals(r1, r4);
        assertEquals(r4, r4);

    }
}