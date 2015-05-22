package com.compomics.commons.model.exceptions;

import java.io.IOException;

/**
 * Created by Davy Maddelein on 23/04/2015.
 */
public class StoreAccessException extends IOException {

    public StoreAccessException(String s) {
        super(s);
    }
}
