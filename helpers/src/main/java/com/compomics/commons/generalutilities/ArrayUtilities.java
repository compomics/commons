package com.compomics.commons.generalutilities;

/**
 * Created by Davy Maddelein on 28/04/2015.
 */
public class ArrayUtilities {

    private ArrayUtilities(){}

    public static int[] consecutiveInts(int n) {
        int[] returnArray = new int[n];
        for (int i = 0; i < n; i++){
            returnArray[i] = i;
        }
        return returnArray;
    }
}
