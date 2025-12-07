package ClientSide;

import javax.swing.*;

 class MyOrdersPage extends JFrame {
    private models.Client currentClient;

    public MyOrdersPage(models.Client client) {
        this.currentClient = client;

        setTitle("My Orders");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton backBtn = new JButton("Back to Main");
        backBtn.setBounds(20, 20, 150, 30);
        add(backBtn);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 70, 840, 380);
        add(scroll);

        backBtn.addActionListener(e -> {
            new MainPage(currentClient).setVisible(true);
            dispose();
        });

        // Load this user's orders
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
    }
}
