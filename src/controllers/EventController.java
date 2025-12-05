package controllers;

import dao.EventDao;
import models.Event;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import java.util.List;

public class EventController {
    private EventDao eventDao = new EventDao();
    private Gson gson = new Gson();

    public Object getAllEvents(Request req, Response res) {
        List<Event> events = eventDao.getAllEvents();
        res.type("application/json");
        return gson.toJson(events);
    }

    public Object getEventById(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Event event = eventDao.getEventById(id);
        res.type("application/json");
        return event != null ? gson.toJson(event) : "{}";
    }

    public Object addEvent(Request req, Response res) {
        Event event = gson.fromJson(req.body(), Event.class);
        boolean success = eventDao.addEvent(event);
        res.type("application/json");
        res.status(success ? 201 : 400);
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    public Object updateEvent(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        Event event = gson.fromJson(req.body(), Event.class);
        boolean success = eventDao.updateEvent(id, event);
        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }

    public Object deleteEvent(Request req, Response res) {
        int id = Integer.parseInt(req.params(":id"));
        boolean success = eventDao.deleteEvent(id);
        res.type("application/json");
        return "{\"status\":\"" + (success ? "success" : "error") + "\"}";
    }
}
