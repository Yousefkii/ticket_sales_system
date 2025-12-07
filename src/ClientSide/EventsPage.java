package ClientSide;

import javax.swing.*;

public class EventsPage extends JFrame {
    private models.Client currentClient;
    private JTextArea displayArea;

    public EventsPage(models.Client client) {
        this.currentClient = client;

        setTitle("Events");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);



        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));  // <- here
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 60, 840, 380);
        add(scroll);


        JButton backBtn = new JButton("Back to Main");
        backBtn.setBounds(20, 20, 150, 30);
        add(backBtn);

        JButton buyBtn = new JButton("Buy Ticket for Event");
        buyBtn.setBounds(200, 20, 200, 30);
        add(buyBtn);

        backBtn.addActionListener(e -> {
            new MainPage(currentClient).setVisible(true);
            dispose();
        });

        // initial load
        loadEvents();

        buyBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Event ID to buy a ticket for:");
            if (input == null || input.isBlank()) return;

            int eventId;
            try {
                eventId = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
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
                // refresh list from scratch
                loadEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Ticket sold but order not saved.");
            }
        });
    }

    private void loadEvents() {
        dao.EventDao eventDao = new dao.EventDao();
        dao.TicketDao ticketDao = new dao.TicketDao();
        java.util.List<models.Event> events = eventDao.getAllEvents();

        if (events.isEmpty()) {
            displayArea.setText("No events available.");
            return;
        }

        StringBuilder sb = new StringBuilder("All events:\n");
        for (models.Event ev : events) {
            int available = ticketDao.countAvailableTicketsForEvent(ev.getId());
            sb.append("ID: ").append(ev.getId())
                    .append(", Name: ").append(ev.getName())
                    .append(", Date: ").append(ev.getDate())
                    .append(", Venue: ").append(ev.getVenue())
                    .append(", Available tickets: ")
                    .append(available > 0 ? available : "SOLD OUT")
                    .append("\n");
        }
        displayArea.setText(sb.toString());
    }
}
