package edu.frostburg.cosc444.threading;

/**
 * An internal "cursor" I use that acts as a way to have a thread query a specific record and
 * in all avoids threads from querying the same record more than once
 * I use the LIMIT keyword in SQL in tandem with this integer to move along the records
 */

public class Counter{
    private static volatile int count = 0;

    public static int getCount(){
        return count;
    }

    public static void incrementCount(){
        count++;
    }
}
