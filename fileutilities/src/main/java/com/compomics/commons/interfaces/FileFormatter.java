package com.compomics.commons.interfaces;

import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Davy Maddelein on 05/10/2015.
 */
public interface FileFormatter<T,U> {


    //TODO: 4/22/2016 discuss if to add formatting rules

    T toFormat(U out);

    U fromFormat(T in) throws UnsupportedEncodingException, MalformedFileException;
}
