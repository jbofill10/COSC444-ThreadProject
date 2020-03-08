package edu.frostburg.cosc444.sql;

import edu.frostburg.cosc444.threading.Counter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class responsible for querying and formating the results from the SQL table
 */
public class Query implements Runnable {
    private Connection conn;
    private ReentrantLock lock;

    public Query(Connection conn, ReentrantLock lock){
        this.conn = conn;
        this.lock = lock;

    }

    @Override
    public void run() {

        try {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM Barcelona.accidents");

            rs.next();
            int size = rs.getInt("total");
            rs.close();

            while(Counter.getCount() < size-1){
                this.lock.lock();
                rs= statement.executeQuery("SELECT * FROM Barcelona.accidents ORDER BY id DESC LIMIT "+ Counter.getCount() +",1");

                while(rs.next())
                    System.out.printf("%-10s " +
                                    "| %-20s " +
                                    "| %-40s " +
                                    "| %-40s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s " +
                                    "| %-10s\n",
                            rs.getString("id"),
                            rs.getString("District_Name"),
                            rs.getString("Neighborhood_Name"),
                            rs.getString("Street"),
                            rs.getString("Weekday"),
                            rs.getString("Month"),
                            rs.getInt("Day"),
                            rs.getInt("Hour"),
                            rs.getString("Part_of_the_day"),
                            rs.getInt("mild_injuries"),
                            rs.getInt("serious_injuries"),
                            rs.getInt("victims"),
                            rs.getInt("vehicles_involved"),
                            rs.getDouble("longitude"),
                            rs.getDouble("latitude"));
                rs.close();
                Counter.incrementCount();
                this.lock.unlock();

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

