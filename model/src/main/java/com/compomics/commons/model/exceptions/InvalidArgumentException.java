package com.compomics.commons.model.exceptions;

/**
 * Created by Davy Maddelein on 19/05/2015.
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(String reason){
        super(reason);
    }

}
