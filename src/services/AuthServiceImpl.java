package services;

import dao.ClientDao;
import models.Client;

public class AuthServiceImpl implements AuthService {

    private final ClientDao clientDao;

    public AuthServiceImpl() {
        this.clientDao = new ClientDao();
    }

    public AuthServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client login(String name, String password) {
        if (name == null || password == null) {
            return null;
        }
        if (name.isEmpty() || password.isEmpty()) {
            return null;
        }
        return clientDao.login(name, password);
    }

    @Override
    public SignupResult signup(String name, String email, String password, String confirm) {
        // basic validation (what you currently do in the button)
        if (name == null || email == null || password == null || confirm == null) {
            return new SignupResult(false, "All fields are required");
        }
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new SignupResult(false, "All fields are required");
        }
        if (!password.equals(confirm)) {
            return new SignupResult(false, "Passwords do not match");
        }

        Client client = new Client(0, name, email, password);
        boolean ok = clientDao.addClient(client);

        if (ok) {
            return new SignupResult(true, "Account created successfully");
        } else {
            return new SignupResult(false, "Error while signing up (DB insert failed)");
        }
    }
}
