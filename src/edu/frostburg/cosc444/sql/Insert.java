package edu.frostburg.cosc444.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Class that reads the values from the .csv file and inserts them into the SQL table
 */
public class Insert {
    public void populate(Connection conn){
        int rowsAdded = 0;
        String line = "", stmt = "";
        try {
            Statement statement = conn.createStatement();

            try{
                BufferedReader reader = new BufferedReader(new FileReader("accidents_2017.csv"));

                while((line = reader.readLine()) != null){
                    line = line.replaceAll("\\s+", "");
                    List<String> row = Arrays.asList(line.split(","));
                    stmt = String.format("INSERT INTO Barcelona.accidents VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %s, %s, \"%s\", %s, %s, %s, %s, %s, %s)",
                            row.get(0), row.get(1), row.get(2), row.get(3),
                            row.get(4), row.get(5), row.get(6), row.get(7), row.get(8), row.get(9),
                            row.get(10), row.get(11), row.get(12), row.get(13), row.get(14));

                    System.out.println(stmt);

                    try{
                        statement.executeUpdate(stmt);
                    }catch (SQLIntegrityConstraintViolationException e){

                    }

                    rowsAdded++;
                }

                statement.close();

                System.out.printf("Population successful! Rows added: %s", rowsAdded);

            }catch (Exception e){
                System.out.println(e);
            }
        }catch (Exception e) {
            System.out.println(e);
        }


    }
}
