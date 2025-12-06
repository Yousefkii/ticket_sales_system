package models;

public class Order {
    private int id;
    private int userId;
    private int ticketId;
    private String orderDate;
    private double totalPrice;

    public Order(int id, int userId, int ticketId, String orderDate, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.ticketId = ticketId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getTicketId() { return ticketId; }
    public String getOrderDate() { return orderDate; }
    public double getTotalPrice() { return totalPrice; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
