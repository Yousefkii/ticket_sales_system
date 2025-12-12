package dao;

import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDaoTest {

    private final ClientDao clientDao = new ClientDao();
    private static final String TEST_EMAIL = "crud-test-client@example.com";

    private void cleanupTestClient() {
        Client existing = clientDao.getClientByEmail(TEST_EMAIL);
        if (existing != null) {
            clientDao.deleteClient(existing.getId());
        }
    }

    @BeforeEach
    void setUp() {
        cleanupTestClient();
    }

    @AfterEach
    void tearDown() {
        cleanupTestClient();
    }

    @Test
    void testAddClientAndGetByEmailAndId() {
        Client client = new Client(0, "Test User", TEST_EMAIL, "secret123");

        boolean inserted = clientDao.addClient(client);
        assertTrue(inserted, "addClient should return true for successful insert");

        Client fromEmail = clientDao.getClientByEmail(TEST_EMAIL);
        assertNotNull(fromEmail, "Client should be found by email after insert");
        assertEquals("Test User", fromEmail.getName());
        assertEquals(TEST_EMAIL, fromEmail.getEmail());
        assertEquals("secret123", fromEmail.getPassword());

        int id = fromEmail.getId();
        assertTrue(id > 0, "Inserted client should have a positive id");

        Client fromId = clientDao.getClientById(id);
        assertNotNull(fromId, "Client should be found by id after insert");
        assertEquals("Test User", fromId.getName());
        assertEquals(TEST_EMAIL, fromId.getEmail());
        assertEquals("secret123", fromId.getPassword());
    }

    @Test
    void testUpdateClient() {
        Client original = new Client(0, "Original Name", TEST_EMAIL, "pass1");
        assertTrue(clientDao.addClient(original), "Initial insert should succeed");

        Client fromEmail = clientDao.getClientByEmail(TEST_EMAIL);
        assertNotNull(fromEmail, "Client should exist before update");
        int id = fromEmail.getId();

        Client updated = new Client(id, "Updated Name", TEST_EMAIL, "newPass");
        boolean updatedResult = clientDao.updateClient(id, updated);
        assertTrue(updatedResult, "updateClient should return true for successful update");

        Client afterUpdate = clientDao.getClientById(id);
        assertNotNull(afterUpdate, "Client should still exist after update");
        assertEquals("Updated Name", afterUpdate.getName());
        assertEquals(TEST_EMAIL, afterUpdate.getEmail());
        assertEquals("newPass", afterUpdate.getPassword());
    }

    @Test
    void testDeleteClient() {
        Client client = new Client(0, "To Delete", TEST_EMAIL, "deleteMe");
        assertTrue(clientDao.addClient(client), "Initial insert should succeed");

        Client fromEmail = clientDao.getClientByEmail(TEST_EMAIL);
        assertNotNull(fromEmail, "Client should exist before delete");
        int id = fromEmail.getId();

        boolean deleted = clientDao.deleteClient(id);
        assertTrue(deleted, "deleteClient should return true for successful delete");

        Client afterDeleteById = clientDao.getClientById(id);
        Client afterDeleteByEmail = clientDao.getClientByEmail(TEST_EMAIL);
        assertNull(afterDeleteById, "Client should not be found by id after delete");
        assertNull(afterDeleteByEmail, "Client should not be found by email after delete");
    }

    @Test
    void testGetAllClientsContainsInsertedClient() {
        Client client = new Client(0, "List User", TEST_EMAIL, "listPass");
        assertTrue(clientDao.addClient(client), "Initial insert should succeed");

        List<Client> all = clientDao.getAllClients();
        assertNotNull(all, "getAllClients should never return null");

        boolean found = all.stream()
                .anyMatch(c -> TEST_EMAIL.equals(c.getEmail())
                        && "List User".equals(c.getName())
                        && "listPass".equals(c.getPassword()));

        assertTrue(found, "Inserted test client should appear in getAllClients result");
    }

    @Test
    void testLoginWithCorrectAndWrongPassword() {
        Client client = new Client(0, "Login User", TEST_EMAIL, "loginPass");
        assertTrue(clientDao.addClient(client), "Initial insert should succeed");

        Client success = clientDao.login("Login User", "loginPass");
        assertNotNull(success, "Login should succeed with correct credentials");
        assertEquals(TEST_EMAIL, success.getEmail());

        Client fail = clientDao.login("Login User", "wrongPass");
        assertNull(fail, "Login should fail with wrong password");
    }
}
