package com.compomics.commons.parsers.iterators;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.formatters.FastaFormatter;
import com.compomics.commons.model.Protein;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.*;
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

    public FastaFileIterator(Path fastaFilePath) throws IOException {
        this(fastaFilePath,">");
    }

    public FastaFileIterator(Path fastaFilePath,String blockSeparator) throws IOException {
        reader = new LineReader(new FileReader(fastaFilePath.toFile()));
        header = reader.readLine();
        //check if we actually have a line that starts with the character we need
        while (header != null && !header.startsWith(blockSeparator)){
            header = reader.readLine();
        }
            if (header == null){
                throw new EOFException("reached end of file without reading a line starting with "+blockSeparator);
            }
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
        try {
            String temp = reader.readLine();
            StringBuilder sequence = new StringBuilder(header);
            while (temp != null && !temp.contains(">")){
                sequence.append(temp);
                temp = reader.readLine();
            }
            header = temp;
            return fastaFormatter.fromFormat(sequence.toString());
        } catch (MalformedFileException|IOException e) {
            NoSuchElementException ex = new NoSuchElementException("could not parse ");
            ex.initCause(e);
            throw ex;
        }
    }
}
