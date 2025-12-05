package dao;

import db.Database;
import models.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, eventId, seatNumber, price, status FROM Tickets";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(
                            rs.getInt("id"),
                            rs.getInt("eventId"),
                            rs.getString("seatNumber"),
                            rs.getDouble("price"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tickets: " + e.getMessage());
        }
        return tickets;
    }

    public Ticket getTicketById(int id) {
        String sql = "SELECT id, eventId, seatNumber, price, status FROM Tickets WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getInt("id"),
                        rs.getInt("eventId"),
                        rs.getString("seatNumber"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ticket: " + e.getMessage());
        }
        return null;
    }

    public boolean addTicket(Ticket ticket) {
        String sql = "INSERT INTO Tickets (eventId, seatNumber, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticket.getEventId());
            pstmt.setString(2, ticket.getSeatNumber());
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setString(4, ticket.getStatus());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error adding ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTicket(int id, Ticket ticket) {
        String sql = "UPDATE Tickets SET eventId = ?, seatNumber = ?, price = ?, status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticket.getEventId());
            pstmt.setString(2, ticket.getSeatNumber());
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setString(4, ticket.getStatus());
            pstmt.setInt(5, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTicket(int id) {
        String sql = "DELETE FROM Tickets WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ticket: " + e.getMessage());
            return false;
        }
    }
}
