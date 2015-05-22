package com.compomics.commons.csharpadapters;

/**
 * Created by Davy Maddelein on 28/04/2015.
 */
public class CSharpStringHash {

    public static int getHash(String aString){
        int num1 = 352654597;
        int num2 = num1;
        //todo c sharp stores a character in two bytes, emulate this behaviour
        int numPtr = 2;
        int length = aString.length();
        while (length > 2)
        {
            num1 = (num1 << 5) + num1 + (num1 >> 27) ^ numPtr;
            num2 = (num2 << 5) + num2 + (num2 >> 27) ^ (numPtr >> 8);
            numPtr += 2;
            length -= 4;
        }
        if (length > 0)
            num1 = (num1 << 5) + num1 + (num1 >> 27) ^ numPtr;
        return num1 + num2 * 1566083941;

    }
}
