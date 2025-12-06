package ClientSide;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class Signup extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton, returnButton;
    private JLabel userLabel, passLabel, confirmLabel, emailLabel, infoLabel;

    public Signup() {
        setTitle("Sign Up Page");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        userLabel = new JLabel("User name:");
        userLabel.setBounds(220, 80, 120, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(360, 80, 320, 30);
        add(usernameField);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(220, 140, 120, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(360, 140, 320, 30);
        add(passwordField);

        confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(220, 200, 140, 30);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(360, 200, 320, 30);
        add(confirmPasswordField);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(220, 260, 120, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(360, 260, 320, 30);
        add(emailField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(360, 340, 130, 40);
        add(submitButton);

        infoLabel = new JLabel("Already have an account?");
        infoLabel.setBounds(220, 400, 180, 30);
        add(infoLabel);

        returnButton = new JButton("Log In");
        returnButton.setBounds(400,400,100, 30);
        add(returnButton);

        JTextField nameField = new JTextField(20);
        JFrame frame = new JFrame("Sign Up");

        // Optional: add logic to switch back to Login or other action
        // Example action
        submitButton.addActionListener(e -> {

            System.out.println("Sign up button clicked!");
            String name = usernameField.getText();              // use the visible username field
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirm  = new String(confirmPasswordField.getPassword());

            System.out.println("Collected fields: " + name + ", " + email);

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required");
                return;
            }
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
                return;
            }

            try {
                dao.ClientDao clientDao = new dao.ClientDao();
                models.Client client = new models.Client(0, name, email, password); // id=0, DB generates real id

                boolean ok = clientDao.addClient(client);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    models.Client clientt = new models.Client(0, name, email, password);
                    new MainPage(clientt).setVisible(true);
                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Error while signing up (DB insert failed).");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error while signing up: " + ex.getClass().getName() + " - " + ex.getMessage());
            }
        });


        returnButton.addActionListener(e -> {
            new Login().setVisible(true);
            this.dispose();

        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup().setVisible(true));
    }
}

