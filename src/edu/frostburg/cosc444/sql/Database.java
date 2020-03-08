package edu.frostburg.cosc444.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This is what facilitates the connection to the DB
 */
public class Database {
    private Connection connection;

    public Database(){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Barcelona",
                    "jbofill",
                    "BAMF");

        } catch (Exception e) {
            System.out.println("\nUnable to connect to SQL DB!\n");
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }
}
