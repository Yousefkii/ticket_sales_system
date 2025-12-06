package controllers;

import dao.TicketDao;
import models.Ticket;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import java.util.List;

public class TicketController {
    private TicketDao ticketDao = new TicketDao();
    private Gson gson = new Gson();

    public Object getAllTickets(Request req, Response res) {
        List<Ticket> tickets = ticketDao.getAllTickets();
        res.type("application/json");
        return gson.toJson(tickets);
    }

    public Object getTicketById(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Ticket ticket = ticketDao.getTicketById(id);
        res.type("application/json");
        return ticket != null ? gson.toJson(ticket) : "{}";
    }

    public Object addTicket(Request req, Response res) {
        Ticket ticket = gson.fromJson(req.body(), Ticket.class);
        boolean success = ticketDao.addTicket(ticket);
        res.type("application/json");
        res.status(success ? 201 : 400);
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    public Object updateTicket(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Ticket ticket = gson.fromJson(req.body(), Ticket.class);

        // choose what “update” means; e.g. only status for now:
        boolean success = ticketDao.updateTicket(id, ticket.getStatus());

        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }


    public Object deleteTicket(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        boolean success = ticketDao.deleteTicket(id);
        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }
}
