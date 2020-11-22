package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserStoriesTest {
    @Test
    void testEqual() {
        UserStory u1 = new UserStory();
        UserStory u2 = new UserStory(1, "US trop bien", "High", 2, null);
        UserStory u3 = new UserStory(1, "US trop bien", "High", 2, null);
        UserStory u4 = new UserStory(1, "US trop bien", "High", 3, null);

        assertNotEquals(u1, u2);
        assertEquals(u2, u3);
        assertNotEquals(u3, u4);
    }
}