package com.compomics.commons.parsers.iterators;

import com.compomics.commons.model.Spectrum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by Davy Maddelein on 10/06/2015.
 */
public class MGFFileIterator implements Iterator<Spectrum>{

    LineNumberReader reader;


   public MGFFileIterator(Path MGFFilePath,String blockSeparator) throws FileNotFoundException {
     reader  = new LineNumberReader(new FileReader(MGFFilePath.toFile()));
   }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Spectrum next() {
        return null;
    }
}
