package models;

public class Event {
    private int id;
    private String name;
    private String date;
    private String venue;
    private String description;

    public Event(int id, String name, String date, String venue, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setVenue(String venue) { this.venue = venue; }
    public void setDescription(String description) { this.description = description; }
}
