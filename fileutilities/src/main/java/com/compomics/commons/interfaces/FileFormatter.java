package com.compomics.commons.interfaces;

import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Davy Maddelein on 05/10/2015.
 */
public interface FileFormatter<T,U> {

    T toFormat(U out);

    U fromFormat(T in) throws UnsupportedEncodingException, MalformedFileException;
}
