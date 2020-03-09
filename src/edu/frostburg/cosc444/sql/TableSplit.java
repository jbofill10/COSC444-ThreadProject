package edu.frostburg.cosc444.sql;

/**
 * Simple little object that stores the start and end indexes of where to query from
 * so each thread doesn't query the same record
 */
public class TableSplit {
    private int start;
    private int end;

    public TableSplit(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }
}
