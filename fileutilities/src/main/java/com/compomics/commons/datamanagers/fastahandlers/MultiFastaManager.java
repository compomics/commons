package com.compomics.commons.datamanagers.fastahandlers;

import com.compomics.commons.caches.SimpleCache;
import com.compomics.commons.interfaces.StoreManager;
import com.compomics.commons.interfaces.StoreStrategy;
import com.compomics.commons.model.exceptions.InvalidArgumentException;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.model.flyweights.Protein;
import com.compomics.commons.model.exceptions.StoreAccessException;
import com.compomics.commons.parsers.FastaParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * manages multiple fasta files under one unifying access point
 * Created by Davy Maddelein on 24/04/2015.
 */
public class MultiFastaManager implements StoreManager<String, Protein> {

    //todo finish this
    private List<SimpleCache<String, Protein>> fastaFiles = new ArrayList<>();

    private FastaParser parser = new FastaParser();

    public MultiFastaManager() {

    }

    public MultiFastaManager(int numberOfFiles, int cacheSize) throws IOException, MalformedFileException {
        for (int i = 0; i < numberOfFiles; i++) {
            fastaFiles.add(new SimpleCache<>(cacheSize));
        }
    }


    public void setFastaFile(Path originalFastaFile) throws IOException, MalformedFileException {
        setFastaFile(originalFastaFile, ">");
    }

    public void setFastaFile(Path originalFastaFile, String headerIdentifier) throws IOException, MalformedFileException {
        SimpleCache<String, Protein> cached = new SimpleCache<>();
        cached.accept(parser.parse(originalFastaFile, headerIdentifier));
        fastaFiles.add(cached);
    }

    @Override
    public Protein retrieveFromStore(String key) throws InvalidArgumentException, StoreAccessException {
        return null;
    }

    @Override
    public Collection<Protein> retrieveMultipleFromStore(Collection<String> keys) throws InvalidArgumentException, StoreAccessException {
        return null;
    }

    @Override
    public void addToStore(Protein entry) throws StoreAccessException {

    }

    @Override
    public void addMultipleToStore(Collection<? extends Protein> entryCollection) throws StoreAccessException {

    }

    @Override
    public void switchStorageStrategies(StoreStrategy<String, Protein> newStorageStrategy) throws StoreAccessException {

    }

    @Override
    public boolean storeContains(String accession) throws StoreAccessException, InvalidArgumentException {
        return false;
    }

    @Override
    public Collection<Protein> retrieveAllFromStore() throws StoreAccessException, InvalidArgumentException {
        return null;
    }
}
