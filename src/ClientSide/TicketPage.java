package ClientSide;

import javax.swing.*;

public class TicketPage extends JFrame {
    private models.Client currentClient;

    public TicketPage(models.Client client) {
        this.currentClient = client;

        setTitle("View Ticket");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton backBtn = new JButton("Back to Main");
        backBtn.setBounds(20, 20, 150, 30);
        add(backBtn);

        JLabel label = new JLabel("Enter Ticket ID:");
        label.setBounds(200, 20, 120, 30);
        add(label);

        JTextField ticketField = new JTextField();
        ticketField.setBounds(320, 20, 100, 30);
        add(ticketField);

        JButton viewBtn = new JButton("View");
        viewBtn.setBounds(430, 20, 80, 30);
        add(viewBtn);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 70, 840, 380);
        add(scroll);

        backBtn.addActionListener(e -> {
            new MainPage(currentClient).setVisible(true);
            dispose();
        });

        viewBtn.addActionListener(e -> {
            String input = ticketField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a ticket ID.");
                return;
            }
            int ticketId;
            try {
                ticketId = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ticket ID must be a number.");
                return;
            }

            dao.TicketDao ticketDao = new dao.TicketDao();
            models.Ticket t = ticketDao.getTicketById(ticketId);

            if (t == null) {
                displayArea.setText("No ticket found with ID " + ticketId);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Ticket details:\n");
            sb.append("ID: ").append(t.getId()).append("\n");
            sb.append("Event ID: ").append(t.getEventId()).append("\n");
            sb.append("Seat: ").append(t.getSeatNumber()).append("\n");
            sb.append("Price: ").append(t.getPrice()).append("\n");
            sb.append("Status: ").append(t.getStatus()).append("\n");

            displayArea.setText(sb.toString());
        });
    }
}
