package Server;

import db.Database;

public class ServerHealth {

    public static boolean isDatabaseHealthy() {
        return Database.testConnection();
    }

    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");

        if (isDatabaseHealthy()) {
            System.out.println("✓ Connection successful!");
        } else {
            System.out.println("✗ Connection failed!");
        }
    }
}
