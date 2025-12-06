package controllers;

import dao.OrderDao;
import models.Order;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import java.util.List;

public class OrderController {
    private OrderDao orderDao = new OrderDao();
    private Gson gson = new Gson();

//    public Object getAllOrders(Request req, Response res) {
//        List<Order> orders = orderDao.getAllOrders();
//        res.type("application/json");
//        return gson.toJson(orders);
//    }
//
//    public Object getOrderById(Request req, Response res) {
//        int id = Integer.parseInt(req.params(":id"));
//        Order order = orderDao.getOrderById(id);
//        res.type("application/json");
//        return order != null ? gson.toJson(order) : "{}";
//    }
//
//    public Object addOrder(Request req, Response res) {
//        Order order = gson.fromJson(req.body(), Order.class);
//        boolean success = orderDao.addOrder(order);
//        res.type("application/json");
//        res.status(success ? 201 : 400);
//        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
//    }
//
//    public Object updateOrder(Request req, Response res) {
//        int id = Integer.parseInt(req.params(":id"));
//        Order order = gson.fromJson(req.body(), Order.class);
//        boolean success = orderDao.updateOrder(id, order);
//        res.type("application/json");
//        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
//    }
//
//    public Object deleteOrder(Request req, Response res) {
//        int id = Integer.parseInt(req.params(":id"));
//        boolean success = orderDao.deleteOrder(id);
//        res.type("application/json");
//        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
//    }
}
