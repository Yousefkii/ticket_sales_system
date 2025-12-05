package controllers;

import dao.ClientDao;
import models.Client;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import java.util.List;

public class ClientController {
    private ClientDao clientDao = new ClientDao();
    private Gson gson = new Gson();

    public Object getAllClients(Request req, Response res) {
        List<Client> clients = clientDao.getAllClients();
        res.type("application/json");
        return gson.toJson(clients);
    }

    public Object getClientById(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Client client = clientDao.getClientById(id);
        res.type("application/json");
        return client != null ? gson.toJson(client) : "{}";
    }

    public Object addClient(Request req, Response res) {
        Client client = gson.fromJson(req.body(), Client.class);
        boolean success = clientDao.addClient(client);
        res.type("application/json");
        res.status(success ? 201 : 400);
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    public Object updateClient(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Client client = gson.fromJson(req.body(), Client.class);
        boolean success = clientDao.updateClient(id, client);
        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    public Object deleteClient(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        boolean success = clientDao.deleteClient(id);
        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    // Optionally, add endpoints for login/signup using getClientByEmail, etc.
    public Object signup(spark.Request req, spark.Response res) {
        System.out.println("==> /api/clients/signup HIT");
        System.out.println("Body: " + req.body());
        res.type("application/json");
        res.status(201);
        return "{\"status\":\"success\"}";
    }

    public Object login(Request req, Response res) {
        Client reqClient = gson.fromJson(req.body(), Client.class);
        Client dbClient = clientDao.getClientByEmail(reqClient.getEmail());
        boolean success = (dbClient != null && dbClient.getPassword().equals(reqClient.getPassword()));
        res.type("application/json");
        if (success) {
            res.status(200);
            return "{\"status\":\"success\"}";
        } else {
            res.status(401);
            return "{\"status\":\"error\"}";
        }
    }
}