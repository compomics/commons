package com.compomics.commons.indexers;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.interfaces.BlockReader;
import com.compomics.commons.model.Protein;
import com.compomics.commons.model.Range;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Davy Maddelein on 07/05/2015.
 */
public class FastaIndexer implements BlockReader<Protein> {

    private Map<String, Range> indexHolder = new HashMap<>();
    private Path currentFasta;


    /**
     * index the passed file according to the fasta format where the passed block separator should indicate the start of a header
     * usually this is a greater than sign (>)
     *
     * @param file           the fasta file to index
     * @param blockSeparator the character sequence that defines the start of a header
     * @throws IOException            if the file was not readable for any reason
     * @throws MalformedFileException if the file could not be parsed as a fasta file for any reason
     */
    @Override
    public void indexFileIntoBlocks(Path file, String blockSeparator) throws IOException, MalformedFileException {
        //THE most naive implementation, structurally and computationally
        currentFasta = file;
        try (LineReader reader = new LineReader(new FileReader(file.toFile()))) {
            String line = reader.readLine();
            StringBuilder sequenceBuilder = new StringBuilder();
            String header = "";
            long startIndexLocation = 0;
            while (line != null) {
                if (line.startsWith(blockSeparator)) {
                    if (sequenceBuilder.length() > 0) {
                        //
                        indexHolder.put(header, new Range(startIndexLocation,  startIndexLocation + header.length() + sequenceBuilder.toString().length()));
                        startIndexLocation += startIndexLocation + header.length() + sequenceBuilder.toString().length();
                    }
                    header = line;
                } else {
                    sequenceBuilder.append(line);
                }
                line = reader.readLine();
            }
        } catch (Exception ex) {
            MalformedFileException mfx = new MalformedFileException("the passed fasta is not in a parseable format");
            mfx.initCause(ex);
            indexHolder.clear();
            throw mfx;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Protein readBlock(Range range) throws IOException {
        int bytesRead;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(currentFasta.toFile()))) {
            try {
                reader.skip((range.getStart().longValue() - 1));
                String line = reader.readLine();
                // we are operating on blind faith that the range will be correct, otherwise the result will be nonsensical
                if (line != null) {
                    bytesRead = line.length();
                    Protein newProtein = new Protein(line);
                    StringBuilder sequenceBuilder = new StringBuilder();
                    while (bytesRead < (range.getEnd().longValue() - range.getStart().longValue())) {
                        sequenceBuilder.append(line);
                        line = reader.readLine();
                        bytesRead = line.length();
                    }
                    newProtein.setSequence(sequenceBuilder.toString());
                    return newProtein;
                } else {
                    throw new IOException("could not read from file properly with the given range");
                }

            } catch (IOException e) {
                //add some more detail to the error message in case of reading problems
                StringBuilder builder = new StringBuilder("could not read block ");
                if (reader.getLineNumber() > 0) {
                    builder.append("at line ").append(reader.getLineNumber());
                } else {
                    builder.append("from range point ").append(range.getStart());
                    if (range.getEnd().longValue() > range.getStart().longValue()) {
                        builder.append(" to range point ").append(range.getEnd());
                    } else {
                        builder.append(", end range was: ").append(range.getEnd());
                    }
                }
                IOException ex = new IOException(builder.toString());
                ex.initCause(e);
                throw ex;
            } catch (NullPointerException e) {
                IOException ex = new IOException();
                ex.initCause(e);
                throw ex;
            }
        }
    }

    public Protein readBlock(String key) throws IOException {
        return readBlock(indexHolder.get(key));
    }

    public boolean indexContainsKey(String key) {
        return indexHolder.containsKey(key);
    }

    public Map<String, Range> getIndex() {
        return indexHolder;
    }
}
