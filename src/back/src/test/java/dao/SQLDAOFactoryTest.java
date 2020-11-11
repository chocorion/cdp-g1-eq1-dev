package dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLDAOFactoryTest {
    @Test
    void FirstConnection() {
        assertDoesNotThrow(SQLDAOFactory::getConnection);
    }
}