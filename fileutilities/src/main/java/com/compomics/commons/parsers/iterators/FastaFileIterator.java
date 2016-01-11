package com.compomics.commons.parsers.iterators;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.formatters.FastaFormatter;
import com.compomics.commons.model.Protein;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Davy Maddelein on 05/10/2015.
 */
public class FastaFileIterator implements Iterator<Protein> {

    LineReader reader;
    FastaFormatter fastaFormatter = new FastaFormatter();
    String header = "";

    public FastaFileIterator(Path fastaFilePath) throws FileNotFoundException {
        reader = new LineReader(new FileReader(fastaFilePath.toFile()));
    }


    @Override
    public boolean hasNext() {
        if (header != null && header.isEmpty()) {
            try {
                header = reader.readLine();
            } catch (IOException e) {
                return false;
            }
        }
        return header != null && !header.isEmpty();
    }

    //todo clean up

    @Override
    public Protein next() {
        String temp = "";
        StringBuilder sequence = new StringBuilder("");
        while (temp != null && !temp.contains(">")){
            try {
                temp = reader.readLine();
                sequence.append(temp);
            } catch (IOException e) {
                NoSuchElementException ex = new NoSuchElementException("could not parse ");
                ex.initCause(e);
                throw ex;
            }
        }
        header = temp;
        try {
            return fastaFormatter.fromFormat(sequence.toString());
        } catch (UnsupportedEncodingException | MalformedFileException e) {
            NoSuchElementException ex = new NoSuchElementException("could not parse ");
            ex.initCause(e);
            throw ex;
        }
    }
}
