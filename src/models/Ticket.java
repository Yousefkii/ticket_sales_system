package models;

public class Ticket {
    private int id;
    private int eventId;
    private String seatNumber;
    private double price;
    private String status;

    public Ticket(int id, int eventId, String seatNumber, double price, String status) {
        this.id = id;
        this.eventId = eventId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public String getSeatNumber() { return seatNumber; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
}
