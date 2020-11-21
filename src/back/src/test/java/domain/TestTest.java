package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestTest {
    @Test
    void testEqual() {
        domain.Test t1 = new domain.Test();
        domain.Test t2 = new domain.Test("Test important", "Tester que ça marche pas", null, "validate", 1);
        domain.Test t3 = new domain.Test("Test important", "Tester que ça marche pas", null, "validate", 1);
        domain.Test t4 = new domain.Test("Test important", "Tester que ça marche pas", "2020", "validate", 1);

        assertNotEquals(t1, t2);
        assertEquals(t2, t3);
        assertNotEquals(t3, t4);
    }
}