package com.compomics.commons.generalutilities;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class StringUtilities {

    private StringUtilities(){}

    public static List<Character> convertStringToListOfCharacters(String aString) {
        List<Character> list = new ArrayList<>();
        for (char c : aString.toCharArray()) {
            list.add(c);
        }
        return list;
    }

    /**
     * as strings are immutable a copy is made of the string to back the list, all changes to the character list are not reflected in the passed string
     * @param aString
     * @return
     */
    public static List<Character> getListBackedByStringChars(final String aString) {

        return new AbstractList<Character>() {

            char[] chars = aString.toCharArray();

            @Override
            public int size() {
                return chars.length;
            }

            @Override
            public Character get(int index) {
                return chars[index];
            }

            @Override
            public Character set(int index, Character element) {
                chars[index] = element;
                //aString = new String(chars);
                return chars[index];
            }
        };
    }
}

