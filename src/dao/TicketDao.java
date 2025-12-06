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

    public boolean updateTicket(int id, String newStatus) {
        String sql = "UPDATE Tickets SET status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket status: " + e.getMessage());
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

    public int countAvailableTicketsForEvent(int eventId) {
        String sql = "SELECT COUNT(*) AS cnt FROM Tickets WHERE eventId = ? AND status = 'AVAILABLE'";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error counting tickets: " + e.getMessage());
        }
        return 0;
    }

    public Ticket getAvailableTicketForEvent(int eventId) {
        String sql = "SELECT id, eventId, seatNumber, price, status " +
                "FROM Tickets " +
                "WHERE eventId = ? AND status = 'AVAILABLE' " +
                "FETCH FIRST 1 ROWS ONLY";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                            rs.getInt("id"),
                            rs.getInt("eventId"),
                            rs.getString("seatNumber"),
                            rs.getDouble("price"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available ticket: " + e.getMessage());
        }

        return null;
    }


}
