package services;

import models.Client;

public interface AuthService {

    Client login(String name, String password);

    SignupResult signup(String name, String email, String password, String confirm);
}
