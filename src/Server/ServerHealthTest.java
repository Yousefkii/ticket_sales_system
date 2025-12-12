// src/test/java/Server/ServerHealthTest.java
package Server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServerHealthTest {

    @Test
    void databaseConnection_isHealthy() {
        assertTrue(ServerHealth.isDatabaseHealthy());
    }
}
