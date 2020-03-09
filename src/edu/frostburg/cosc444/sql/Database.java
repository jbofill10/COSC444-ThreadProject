package edu.frostburg.cosc444.sql;

import java.sql.*;

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

    /**
     * Gets table count
     * @return
     */
    public int getCount(){
        int size = 0;
        try {
            Database db = new Database();
            Connection conn = db.getConnection();
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM Barcelona.accidents");

            rs.next();
            size = rs.getInt("total");
            rs.close();

            return size;
        }catch (SQLException e){
            System.out.println(e);
        }

        return size;

    }
}
