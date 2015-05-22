package com.compomics.commons.datamanagers.spectrumfilehandlers;

import com.compomics.commons.parsers.MGFParser;
import com.compomics.commons.model.Spectrum;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Davy Maddelein on 30/04/2015.
 */
public class MultipleMGFHandler {

    private List<Spectrum> parsedSpectra = new ArrayList<>(20000);

    private File[] allMgfFilesPassed;

    /**
     * should be used when the spectra have not been merged into a single file but each are in a file separately
     * <p
     * instead of using all of the parsers which is more expensive than just keeping the spectra, this class replaces the caching mechanism after retrieving the spectrum objects from the files
     */
    public MultipleMGFHandler(File... allMGFFilesToParse) {
        this("BEGIN IONS", allMGFFilesToParse);
    }

    public MultipleMGFHandler(String blockSeparator, File... allMGFFilesToParse) {
        List<File> faultyFiles = new ArrayList<>();

        allMgfFilesPassed = allMGFFilesToParse;

        MGFParser indexer = new MGFParser();

        Arrays.stream(allMGFFilesToParse).map(e -> {
            try {
                parsedSpectra = indexer.parse(e.toPath(), blockSeparator);
            } catch (MalformedFileException | IOException e1) {
                faultyFiles.add(e);

            }
            return new ArrayList<Spectrum>();
        });
        if (faultyFiles.size() > 0) {
            //raise recoverable warning if size < all files to parse size
        }
    }

    public List<Spectrum> getAllSpectra() {
       return new ArrayList<Spectrum>(allMgfFilesPassed.length);
    }


}
