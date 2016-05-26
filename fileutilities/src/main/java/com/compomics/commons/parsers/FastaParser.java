package com.compomics.commons.parsers;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.model.exceptions.MalformedFileException;

import com.compomics.commons.model.flyweights.Protein;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


/**
 * a general file parser for a fasta file.
 * Created by Davy Maddelein on 23/04/2015.
 */
public class FastaParser {


    public List<? extends Protein> parse(Path file) throws IOException, MalformedFileException {
        return this.parse(file,">");
    }

    public List<? extends Protein> parse(Path file, String blockSeparator) throws IOException, MalformedFileException {
        //THE most naive implementation, structurally and computationally
        List<Protein> proteinStore = new ArrayList<>();

        try (LineReader reader = new LineReader(new FileReader(file.toFile()))) {
            String line = reader.readLine();
            StringBuilder sequenceBuilder = new StringBuilder();
            String header = "";
            while (line != null) {
                if (line.startsWith(blockSeparator)) {
                    //add limiting check for protein store to avoid growing
                    if (sequenceBuilder.length() > 0) {
                        proteinStore.add(new Protein(header, sequenceBuilder.toString().trim()));
                        sequenceBuilder.setLength(0);
                    }
                    header = line;
                } else {
                    sequenceBuilder.append(line);
                }
                line = reader.readLine();
            }
            proteinStore.add(new Protein(header, sequenceBuilder.toString()));
        } catch (Exception ex) {
            MalformedFileException mfx = new MalformedFileException("the passed fasta is not in a parseable format");
            mfx.initCause(ex);
            proteinStore.clear();
            throw mfx;
        }
        return proteinStore;
    }
}
