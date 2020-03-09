package edu.frostburg.cosc444;

import edu.frostburg.cosc444.sql.Database;
import edu.frostburg.cosc444.sql.Insert;
import edu.frostburg.cosc444.sql.Query;
import edu.frostburg.cosc444.sql.TableSplit;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Works as the main class
 * Initializes connection to SQL DB
 * Prompts user to populate database
 * Invokes run on threads
 */
public class JavaApplication {
    public static void main(String[] args) {
        Database db = new Database();
        Connection connection = db.getConnection();

        Insert insert = new Insert();

        Scanner input = new Scanner(System.in);

       System.out.print("Enter [Y] or [N] to populate the SQL Table: ");
        String result = input.next();

        if(result.toUpperCase().equals("Y"))
            insert.populate(connection);


        System.out.println("Querying table with 8 threads!");


        ExecutorService pool = Executors.newFixedThreadPool(8);

        // Hashmap used to store the start and end for each thread to query 
        HashMap<Integer, TableSplit> tableSplit = new HashMap<>();

        // gets record count in table
        int size = db.getCount();

        /**
         * Weird logic here, but I split the table count by 8 (since 8 threads)
         * So the hashmap has an offset from start of the table. So an offset of 1 = table size / 8 * 1
         * So basically it tells me where to start and when to stop in terms of querying for records
         * For the last split of the table, I also add on the moduolo of the table size to get the last bit of records
         */
        for(int i = 0; i<8; i++){
            if(i == 0) {
                TableSplit tS = new TableSplit((size / 8 * i), (size / 8 * (i + 1)));
                tableSplit.put(i+1, tS);
            }

            else if(i==7){
                TableSplit tS = new TableSplit((size / 8 * i), ((size / 8 * (i + 1)) + size%8));
                tableSplit.put(i+1, tS);
            }

            else {
                TableSplit tS = new TableSplit((size / 8 * i) + 1, (size / 8 * (i + 1)));
                tableSplit.put(i+1, tS);
            }

        }

        Query query1 = new Query(1, tableSplit);
        Query query2 = new Query(2, tableSplit);
        Query query3 = new Query(3, tableSplit);
        Query query4 = new Query(4, tableSplit);
        Query query5 = new Query(5, tableSplit);
        Query query6 = new Query(6, tableSplit);
        Query query7 = new Query(7, tableSplit);
        Query query8 = new Query(8, tableSplit);

        pool.execute(query1);
        pool.execute(query2);
        pool.execute(query3);
        pool.execute(query4);
        pool.execute(query5);
        pool.execute(query6);
        pool.execute(query7);
        pool.execute(query8);

        pool.shutdown();

    }
}
