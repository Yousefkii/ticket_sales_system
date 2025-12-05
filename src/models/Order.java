package models;

public class Order {
    private int id;
    private int customerId;
    private int ticketId; // Simplifying: one ticket per order for now
    private double totalPrice;
    private String status;

    public Order(int id, int customerId, int ticketId, double totalPrice, String status) {
        this.id = id;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public int getTicketId() { return ticketId; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
}
