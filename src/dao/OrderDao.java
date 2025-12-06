package dao;

import db.Database;
import models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public boolean addOrder(Order order) {
        String sql = "INSERT INTO Orders (userId, ticketId, order_date, total_price) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getTicketId());
            pstmt.setString(3, order.getOrderDate());
            pstmt.setDouble(4, order.getTotalPrice());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error adding order: " + e.getMessage());
            return false;
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, userId, ticketId, order_date, total_price " +
                "FROM Orders WHERE userId = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                            rs.getInt("id"),
                            rs.getInt("userId"),
                            rs.getInt("ticketId"),
                            rs.getString("order_date"),
                            rs.getDouble("total_price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
        return orders;
    }
}
