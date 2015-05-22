package com.compomics.commons.model;

/**
 * general class to give a range between two numbers, as this class uses the Number abstraction, please keep in mind the rules when changing the values to a different precision
 * Created by Davy Maddelein on 23/04/2015.
 */
public class Range {

    private Number start;
    private Number end;

    public Range(Number aStart, Number anEnd) {
        start = aStart;
        end = anEnd;
    }

    public Number getStart() {
        return start;
    }

    public Number getEnd() {
        return end;
    }

    public void setEnd(Number end) {
        this.end = end;
    }
}
