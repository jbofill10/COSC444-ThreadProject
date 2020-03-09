package edu.frostburg.cosc444.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Class responsible for querying and formating the results from the SQL table
 */
public class Query implements Runnable {
    private int offset;
    private HashMap<Integer, TableSplit> tableSplit;

    public Query(int offset, HashMap<Integer, TableSplit> tableSplit){
        this.offset = offset;
        this.tableSplit = tableSplit;
    }

    @Override
    public void run() {
        Database db = new Database();
        Connection conn = db.getConnection();

        int start = tableSplit.get(offset).getStart();

        try {
                while(start < tableSplit.get(offset).getEnd()){
                    Statement statement = conn.createStatement();
                    ResultSet rs= statement.executeQuery("SELECT * FROM Barcelona.accidents ORDER BY id DESC LIMIT "+ start +",1");

                    while(rs.next()) {

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
                    }
                    rs.close();
                    start+=1;

                }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

