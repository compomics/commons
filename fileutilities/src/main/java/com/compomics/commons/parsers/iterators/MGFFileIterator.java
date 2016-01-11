package com.compomics.commons.parsers.iterators;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.formatters.MGFFormatter;
import com.compomics.commons.model.Spectrum;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Davy Maddelein on 10/06/2015.
 */
public class MGFFileIterator implements Iterator<Spectrum> {

    LineReader reader;
    String blockSeparator;
    String currentLine;
    MGFFormatter MGFFormatter = new MGFFormatter();

    public MGFFileIterator(Path MGFFilePath, String aBlockSeparator) throws FileNotFoundException {
        reader = new LineReader(new FileReader(MGFFilePath.toFile()));
        blockSeparator = aBlockSeparator;

    }

    @Override
    public boolean hasNext() {
        if(currentLine.equalsIgnoreCase(blockSeparator)){
            return true;
        }
        try {
            while ((currentLine  = reader.readLine()) != null){
                if(currentLine.equalsIgnoreCase(blockSeparator)){
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Spectrum next() {
        StringBuilder builder = new StringBuilder();
        try {
            while ((currentLine = reader.readLine()) != null){
                if(currentLine.equalsIgnoreCase(blockSeparator)){
                    return MGFFormatter.fromFormat(builder.toString());
                }
                builder.append(currentLine);
            }
        } catch (MalformedFileException | IOException e) {
            NoSuchElementException ex = new NoSuchElementException("could not parse ");
            ex.initCause(e);
            throw ex;
        }
        throw new NoSuchElementException();
    }
}
