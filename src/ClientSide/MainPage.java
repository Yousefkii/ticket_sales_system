package ClientSide;

import javax.swing.*;

public class MainPage extends JFrame {
    private models.Client currentClient;

    public MainPage(models.Client client) {
        this.currentClient = client;

        setTitle("Ticket Sales System - Main Page");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Centered welcome label
        JLabel welcome = new JLabel("Welcome, " + client.getName(), SwingConstants.CENTER);
        welcome.setBounds(250, 40, 400, 30);
        add(welcome);

        // Four big buttons in grid
        JButton eventsBtn  = new JButton("View Events");
        JButton ticketBtn  = new JButton("View Ticket");
        JButton buyBtn     = new JButton("Buy Ticket");
        JButton ordersBtn  = new JButton("My Orders");

        int btnWidth = 200;
        int btnHeight = 60;
        int startX = 200;
        int startY = 140;
        int gapX = 240;
        int gapY = 110;

        eventsBtn.setBounds(startX,         startY,          btnWidth, btnHeight);
        ticketBtn.setBounds(startX + gapX,  startY,          btnWidth, btnHeight);
        buyBtn.setBounds(startX,            startY + gapY,   btnWidth, btnHeight);
        ordersBtn.setBounds(startX + gapX,  startY + gapY,   btnWidth, btnHeight);

        add(eventsBtn);
        add(ticketBtn);
        add(buyBtn);
        add(ordersBtn);

        // For now, only wire Events button; others later
        eventsBtn.addActionListener(e -> {
            new EventsPage(currentClient).setVisible(true);
            dispose(); // close menu, or remove this if you want it to stay
        });

        ticketBtn.addActionListener(e -> {
            new TicketPage(currentClient).setVisible(true);
            dispose();
        });

        ordersBtn.addActionListener(e -> {
            new MyOrdersPage(currentClient).setVisible(true);
            dispose();
        });

        buyBtn.addActionListener(e -> {
            new BuyTicketPage(currentClient).setVisible(true);
            dispose();
        });

        // ticketBtn, buyBtn, ordersBtn will later open TicketPage, BuyTicketPage, MyOrdersPage
    }
}
