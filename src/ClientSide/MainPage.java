package ClientSide;

import javax.swing.*;

public class MainPage extends JFrame {
    private models.Client currentClient;

    public MainPage(models.Client client) {
        setTitle("Ticket Sales System - Main Page");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + client.getName());
        welcome.setBounds(20, 20, 300, 30);
        add(welcome);

        JButton viewAllEventsBtn = new JButton("View All Events");
        viewAllEventsBtn.setBounds(20, 70, 150, 30);
        add(viewAllEventsBtn);

        JButton viewEventBtn = new JButton("View Event by ID");
        viewEventBtn.setBounds(180, 70, 180, 30);
        add(viewEventBtn);

        JButton viewTicketBtn = new JButton("View Ticket by ID");
        viewTicketBtn.setBounds(370, 70, 180, 30);
        add(viewTicketBtn);

        JButton buyTicketBtn = new JButton("Buy Ticket");
        buyTicketBtn.setBounds(560, 70, 150, 30);
        add(buyTicketBtn);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 120, 840, 320);
        add(scroll);

        // View all events
        viewAllEventsBtn.addActionListener(e -> {
            dao.EventDao eventDao = new dao.EventDao();
            java.util.List<models.Event> events = eventDao.getAllEvents();

            if (events.isEmpty()) {
                displayArea.setText("No events available.");
                return;
            }

            StringBuilder sb = new StringBuilder("All events:\n");
            for (models.Event ev : events) {
                sb.append("ID: ").append(ev.getId())
                        .append(", Name: ").append(ev.getName())
                        .append(", Date: ").append(ev.getDate())
                        .append(", Venue: ").append(ev.getVenue())
                        .append("\n");
            }
            displayArea.setText(sb.toString());
        });

        // View one event by ID
        viewEventBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Event ID:");
            if (input == null || input.isBlank()) return;
            int eventId = Integer.parseInt(input);

            dao.EventDao eventDao = new dao.EventDao();
            models.Event ev = eventDao.getEventById(eventId);

            if (ev == null) {
                displayArea.setText("No event found with ID " + eventId);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Event details:\n");
            sb.append("ID: ").append(ev.getId()).append("\n");
            sb.append("Name: ").append(ev.getName()).append("\n");
            sb.append("Date: ").append(ev.getDate()).append("\n");
            sb.append("Venue: ").append(ev.getVenue()).append("\n");
            sb.append("Description: ").append(ev.getDescription()).append("\n");

            displayArea.setText(sb.toString());
        });

        // View one ticket by ID
        viewTicketBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Ticket ID:");
            if (input == null || input.isBlank()) return;
            int ticketId = Integer.parseInt(input);

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

        // Buy ticket: use getTicketById + updateTicket(id, ticket)
        buyTicketBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Event ID to buy a ticket for:");
            if (input == null || input.isBlank()) return;
            int eventId = Integer.parseInt(input);

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

            // create order for this user + ticket
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
                        "Ticket bought successfully! Ticket ID: " + t.getId());
                int newAvailable = ticketDao.countAvailableTicketsForEvent(eventId);
                displayArea.setText("Event " + eventId + " now has "
                        + (newAvailable > 0 ? newAvailable : "SOLD OUT") + " tickets available.");
            } else {
                JOptionPane.showMessageDialog(this, "Ticket sold but order not saved.");
            }
        });



        viewAllEventsBtn.addActionListener(e -> {
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
                        .append(", Available tickets: ").append(available > 0 ? available : "SOLD OUT")
                        .append("\n");
            }
            displayArea.setText(sb.toString());
        });

        JButton myOrdersBtn = new JButton("My Orders");
        myOrdersBtn.setBounds(720, 70, 120, 30);
        add(myOrdersBtn);

        myOrdersBtn.addActionListener(e -> {
            dao.OrderDao orderDao = new dao.OrderDao();
            java.util.List<models.Order> orders =
                    orderDao.getOrdersByUserId(currentClient.getId());

            if (orders.isEmpty()) {
                displayArea.setText("You have no orders yet.");
                return;
            }

            StringBuilder sb = new StringBuilder("My Orders:\n");
            for (models.Order o : orders) {
                sb.append("Order ID: ").append(o.getId())
                        .append(", Ticket ID: ").append(o.getTicketId())
                        .append(", Date: ").append(o.getOrderDate())
                        .append(", Price: ").append(o.getTotalPrice())
                        .append("\n");
            }
            displayArea.setText(sb.toString());
        });

    }
}
