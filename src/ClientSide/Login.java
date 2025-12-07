package ClientSide;
import models.Client;
import services.AuthService;
import services.AuthServiceImpl;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;
    private JLabel userLabel, passLabel, infoLabel;
    private final AuthService authService = new AuthServiceImpl();

    public Login() {
        setTitle("Login Page");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        userLabel = new JLabel("User name:");
        userLabel.setBounds(220, 120, 120, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(360, 120, 320, 30);
        add(usernameField);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(220, 180, 120, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(360, 180, 320, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(360, 240, 130, 40);
        add(loginButton);

        infoLabel = new JLabel("Don't have an account?");
        infoLabel.setBounds(220, 320, 180, 30);
        add(infoLabel);

        signUpButton = new JButton("Sign up");
        signUpButton.setBounds(420, 320, 130, 40);
        add(signUpButton);

        // Login action (example)
        loginButton.addActionListener(e -> {
            String name     = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Client client = authService.login(name, password);

            if (client != null) {
                JOptionPane.showMessageDialog(this,
                        "Login successful! Welcome " + client.getName());
                new MainPage(client).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.");
            }
        });




        // Sign up action (example)
        signUpButton.addActionListener(e -> {
                    new Signup().setVisible(true);
                    this.dispose();
                }
        );

    }public static void main (String[]args){
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
