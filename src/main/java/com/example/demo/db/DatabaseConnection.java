package com.example.demo.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/db_fitness";
    private static String username = "root";
    private static String password = "";

    private static Connection connection;
    private static Statement statement;

    public DatabaseConnection() {

    }

    public static void initConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();

        if(connection != null) {
            System.out.println("Connection Established");
        } else {
            System.out.println("Connection Not Established");
        }
    }

    public static Statement getStatement() throws SQLException {
        if(statement == null) {
            initConnection();
        }

        return statement;
    }
}
