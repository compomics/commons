package com.compomics;

/**
 * Created by Davy Maddelein on 06/10/2015.
 */
public class DataPoint {

    private Number x;
    private Number y;

    public DataPoint(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    public Number getX() {
        return x;
    }

    public void setX(Number x) {
        this.x = x;
    }

    public Number getY() {
        return y;
    }

    public void setY(Number y) {
        this.y = y;
    }
}
