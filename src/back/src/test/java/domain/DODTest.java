package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DODTest {
    @Test
    void testEqual() {
        DOD dod1 = new DOD();
        DOD dod2 = new DOD(1, 1, "Description de la DOD", false);
        DOD dod3 = new DOD(1, 1, "Description de la DOD", false);
        DOD dod4 = new DOD(1, 1, "Description de la DOD", true);

        assertNotEquals(dod1, dod2);
        assertEquals(dod2, dod3);
        assertNotEquals(dod3, dod4);
    }
}