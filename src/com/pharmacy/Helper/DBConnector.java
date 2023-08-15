package com.pharmacy.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connect = null;

    public Connection connectDB(){ //Making the connection of the program with the database
        try {
            //this.connect = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
            this.connect = DriverManager.getConnection(Config.connectionUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.connect;
    }

    public static Connection getInstance(){ //Getting the instance to use when calling the queries
        DBConnector db = new DBConnector();
        return db.connectDB();
    }
}
