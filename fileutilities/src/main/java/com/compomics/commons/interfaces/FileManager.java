package com.compomics.commons.interfaces;

import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * needs a better name
 * Created by Davy Maddelein on 08/05/2015.
 */
public interface FileManager<T,U> extends StoreManager<T,U> {

    void setFile(Path file) throws IOException, MalformedFileException;

    void setFile(Path file, String headerIdentifier) throws IOException, MalformedFileException;
}
