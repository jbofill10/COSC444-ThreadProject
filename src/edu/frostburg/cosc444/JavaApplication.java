package edu.frostburg.cosc444;

import edu.frostburg.cosc444.sql.Database;
import edu.frostburg.cosc444.sql.Insert;
import edu.frostburg.cosc444.sql.Query;

import java.sql.Connection;
import java.util.Scanner;
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

        ReentrantLock lock = new ReentrantLock();

        Query query = new Query(connection, lock);
        Query query1 = new Query(connection, lock);
        Query query2 = new Query(connection, lock);
        Query query3 = new Query(connection, lock);
        Query query4 = new Query(connection, lock);
        Query query5 = new Query(connection, lock);
        Query query6 = new Query(connection, lock);
        Query query7 = new Query(connection, lock);

        pool.execute(query);
        pool.execute(query1);
        pool.execute(query2);
        pool.execute(query3);
        pool.execute(query4);
        pool.execute(query5);
        pool.execute(query6);
        pool.execute(query7);

        pool.shutdown();

    }
}
