package ClientSide;

import javax.swing.*;

public class BuyTicketPage extends JFrame {
    private models.Client currentClient;

    public BuyTicketPage(models.Client client) {
        this.currentClient = client;

        setTitle("Buy Ticket");
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton backBtn = new JButton("Back to Main");
        backBtn.setBounds(20, 20, 150, 30);
        add(backBtn);

        JLabel label = new JLabel("Enter Event ID to buy a ticket for:");
        label.setBounds(20, 80, 260, 30);
        add(label);

        JTextField eventField = new JTextField();
        eventField.setBounds(280, 80, 100, 30);
        add(eventField);

        JButton buyBtn = new JButton("Buy");
        buyBtn.setBounds(390, 80, 80, 30);
        add(buyBtn);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(infoArea);
        scroll.setBounds(20, 130, 540, 120);
        add(scroll);

        backBtn.addActionListener(e -> {
            new MainPage(currentClient).setVisible(true);
            dispose();
        });

        buyBtn.addActionListener(e -> {
            String input = eventField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an event ID.");
                return;
            }

            int eventId;
            try {
                eventId = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Event ID must be a number.");
                return;
            }

            dao.TicketDao ticketDao = new dao.TicketDao();
            dao.OrderDao orderDao = new dao.OrderDao();

            int available = ticketDao.countAvailableTicketsForEvent(eventId);
            if (available == 0) {
                JOptionPane.showMessageDialog(this, "Event " + eventId + " is SOLD OUT.");
                return;
            }

            models.Ticket t = ticketDao.getAvailableTicketForEvent(eventId);
            if (t == null) {
                JOptionPane.showMessageDialog(this, "No AVAILABLE ticket found.");
                return;
            }

            boolean ok = ticketDao.updateTicket(t.getId(), "SOLD");
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Could not update ticket.");
                return;
            }

            String today = java.time.LocalDate.now().toString();
            models.Order order = new models.Order(
                    0,
                    currentClient.getId(),
                    t.getId(),
                    today,
                    t.getPrice()
            );
            boolean orderOk = orderDao.addOrder(order);

            if (orderOk) {
                JOptionPane.showMessageDialog(this,
                        "Ticket bought! Ticket ID: " + t.getId());
                int newAvail = ticketDao.countAvailableTicketsForEvent(eventId);
                infoArea.setText("You bought ticket:\n" +
                        "Ticket ID: " + t.getId() + "\n" +
                        "Event ID: " + t.getEventId() + "\n" +
                        "Seat: " + t.getSeatNumber() + "\n" +
                        "Price: " + t.getPrice() + "\n\n" +
                        "Event " + eventId + " now has " +
                        (newAvail > 0 ? newAvail : "SOLD OUT") + " tickets available.");
            } else {
                JOptionPane.showMessageDialog(this, "Ticket sold but order not saved.");
            }
        });
    }
}
