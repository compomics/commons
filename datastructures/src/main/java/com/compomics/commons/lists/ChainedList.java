package com.compomics.commons.lists;

import java.util.ArrayList;


/**
 * Created by Davy Maddelein on 08/05/2015.
 */
public class ChainedList<E> extends ArrayList<E> {

    int locationPointer = -1;


    public E getNext() throws IllegalAccessException {
        locationPointer=+1;
        if (locationPointer > this.size() || locationPointer < 0){
            //return to start
            locationPointer = 0;
        }
        return this.get(locationPointer);
    }

    @Override
    public E get(int index) {
        locationPointer = index;
        return super.get(index);
    }
}
