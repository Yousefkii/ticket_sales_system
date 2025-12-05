package Server;

import db.Database;

public class ServerTest {

    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");

        if (Database.testConnection()) {
            System.out.println("✓ Connection successful!");
        } else {
            System.out.println("✗ Connection failed!");
        }
    }
}