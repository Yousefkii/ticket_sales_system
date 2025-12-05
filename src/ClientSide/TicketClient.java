package ClientSide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Example: TicketClient.java
public class TicketClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public TicketClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String login(String username, String password) throws IOException {
        out.println("LOGIN;" + username + ";" + password);
        return in.readLine();
    }

    public String signUp(String username, String password, String email) throws IOException {
        out.println("SIGNUP;" + username + ";" + password + ";" + email);
        return in.readLine();
    }

    public void close() {
    }

    // Remember to add close() and other useful methods for cleanup
}
