package Server;

import controllers.ClientController;
import controllers.EventController;
import controllers.OrderController;
import controllers.TicketController;
import db.Database;
import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        if (!Database.testConnection()) {
            System.err.println("Failed to connect to database. Exiting...");
            return;
        }
        port(9090);

        ClientController clientController = new ClientController();
        EventController eventController = new EventController();
        OrderController orderController = new OrderController();
        TicketController ticketController = new TicketController();

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.type("application/json");
        });

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        path("/api", () -> {
            // Clients
            get("/clients", clientController::getAllClients);
            get("/clients/:id", clientController::getClientById);
            post("/clients", clientController::addClient);
            put("/clients/:id", clientController::updateClient);
            delete("/clients/:id", clientController::deleteClient);
            post("/clients/signup", clientController::signup);
            post("/clients/login", clientController::login);

            // Events
            get("/events", eventController::getAllEvents);
            get("/events/:id", eventController::getEventById);
            post("/events", eventController::addEvent);
            put("/events/:id", eventController::updateEvent);
            delete("/events/:id", eventController::deleteEvent);

            // Tickets
            get("/tickets", ticketController::getAllTickets);
            get("/tickets/:id", ticketController::getTicketById);
            post("/tickets", ticketController::addTicket);
            put("/tickets/:id", ticketController::updateTicket);
            delete("/tickets/:id", ticketController::deleteTicket);

            // Orders
            get("/orders", orderController::getAllOrders);
            get("/orders/:id", orderController::getOrderById);
            post("/orders", orderController::addOrder);
            put("/orders/:id", orderController::updateOrder);
            delete("/orders/:id", orderController::deleteOrder);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body("{\"error\": \"Internal server error: " + exception.getMessage() + "\"}");
        });

        System.out.println("\n===================================");
        System.out.println("  Ticket Sales Server Started!");
        System.out.println("===================================");
        System.out.println("Server URL: http://localhost:8080");
        System.out.println("\nAPI Endpoints:");
        System.out.println("===================================");
        System.out.println("Clients:");
        System.out.println("  GET    /api/clients          - Get all clients");
        System.out.println("  GET    /api/clients/:id      - Get client by ID");
        System.out.println("  POST   /api/clients          - Add new client");
        System.out.println("  PUT    /api/clients/:id      - Update client");
        System.out.println("  DELETE /api/clients/:id      - Delete client");
        System.out.println("  POST   /api/clients/signup   - Client signup");
        System.out.println("  POST   /api/clients/login    - Client login");
        System.out.println("===================================");
        System.out.println("Events:");
        System.out.println("  GET    /api/events           - Get all events");
        System.out.println("  GET    /api/events/:id       - Get event by ID");
        System.out.println("  POST   /api/events           - Add new event");
        System.out.println("  PUT    /api/events/:id       - Update event");
        System.out.println("  DELETE /api/events/:id       - Delete event");
        System.out.println("===================================");
        System.out.println("Tickets:");
        System.out.println("  GET    /api/tickets          - Get all tickets");
        System.out.println("  GET    /api/tickets/:id      - Get ticket by ID");
        System.out.println("  POST   /api/tickets          - Add new ticket");
        System.out.println("  PUT    /api/tickets/:id      - Update ticket");
        System.out.println("  DELETE /api/tickets/:id      - Delete ticket");
        System.out.println("===================================");
        System.out.println("Orders:");
        System.out.println("  GET    /api/orders           - Get all orders");
        System.out.println("  GET    /api/orders/:id       - Get order by ID");
        System.out.println("  POST   /api/orders           - Add new order");
        System.out.println("  PUT    /api/orders/:id       - Update order");
        System.out.println("  DELETE /api/orders/:id       - Delete order");
        System.out.println("===================================\n");
    }
}
