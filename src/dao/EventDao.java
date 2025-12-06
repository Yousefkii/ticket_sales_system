package dao;

import db.Database;
import models.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    public java.util.List<Event> getAllEvents() {
        java.util.List<Event> events = new java.util.ArrayList<>();
        String sql = "SELECT id, name, event_date, venue, description FROM Events";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("event_date"),
                        rs.getString("venue"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching events: " + e.getMessage());
        }
        return events;
    }

    public Event getEventById(int id) {
        String sql = "SELECT id, name, event_date, venue, description FROM Events WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("event_date"),
                        rs.getString("venue"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching event: " + e.getMessage());
        }
        return null;
    }

    public boolean addEvent(Event event) {
        String sql = "INSERT INTO Events (name, date, venue, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getDate());
            pstmt.setString(3, event.getVenue());
            pstmt.setString(4, event.getDescription());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
            return false;
        }
    }

    public boolean updateEvent(int id, Event event) {
        String sql = "UPDATE Events SET name = ?, date = ?, venue = ?, description = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getDate());
            pstmt.setString(3, event.getVenue());
            pstmt.setString(4, event.getDescription());
            pstmt.setInt(5, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating event: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
            return false;
        }
    }
}
