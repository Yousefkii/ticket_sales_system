// src/test/java/services/AuthServiceImplTest.java
package services;

import dao.ClientDao;
import models.Client;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Test
    void login_withCorrectCredentials_returnsClient() {
        ClientDao mockDao = mock(ClientDao.class);
        Client expected = new Client(1, "john", "john@example.com", "secret");
        when(mockDao.login("john", "secret")).thenReturn(expected);

        AuthServiceImpl service = new AuthServiceImpl(mockDao);

        Client result = service.login("john", "secret");

        assertNotNull(result);
        assertEquals("john", result.getName());
    }

    @Test
    void login_withEmptyName_returnsNull() {
        ClientDao mockDao = mock(ClientDao.class);
        AuthServiceImpl service = new AuthServiceImpl(mockDao);

        Client result = service.login("", "secret");

        assertNull(result);
        verify(mockDao, never()).login(anyString(), anyString());
    }

    @Test
    void signup_withMismatchedPasswords_returnsError() {
        ClientDao mockDao = mock(ClientDao.class);
        AuthServiceImpl service = new AuthServiceImpl(mockDao);

        SignupResult result = service.signup("john", "john@example.com", "a", "b");

        assertFalse(result.isSuccess());
        assertEquals("Passwords do not match", result.getMessage());
        verify(mockDao, never()).addClient(any());
    }

    @Test
    void signup_withValidData_callsDaoAndReturnsSuccess() {
        ClientDao mockDao = mock(ClientDao.class);
        when(mockDao.addClient(any(Client.class))).thenReturn(true);

        AuthServiceImpl service = new AuthServiceImpl(mockDao);

        SignupResult result = service.signup("john", "john@example.com", "secret", "secret");

        assertTrue(result.isSuccess());
        assertEquals("Account created successfully", result.getMessage());
        verify(mockDao, times(1)).addClient(any(Client.class));
    }
}
