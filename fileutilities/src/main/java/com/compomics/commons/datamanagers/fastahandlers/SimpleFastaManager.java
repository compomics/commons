package com.compomics.commons.datamanagers.fastahandlers;

import com.compomics.commons.caches.SimpleCache;
import com.compomics.commons.interfaces.StoreStrategy;
import com.compomics.commons.model.exceptions.InvalidArgumentException;
import com.compomics.commons.parsers.FastaParser;
import com.compomics.commons.indexers.FastaIndexer;
import com.compomics.commons.interfaces.FileManager;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.model.flyweights.Protein;
import com.compomics.commons.model.exceptions.StoreAccessException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Davy Maddelein on 29/04/2015.
 */
public class SimpleFastaManager implements FileManager<String,Protein> {

    FastaParser parser;
    FastaIndexer indexHolder = new FastaIndexer();

    SimpleCache<String, Protein> cache;

    public SimpleFastaManager(){
        cache = new SimpleCache<>(20000);
    }

    public SimpleFastaManager(int cacheSize) throws IOException, MalformedFileException {
        cache = new SimpleCache<>(cacheSize);
    }


    @Override
    public void setFile(Path file) throws IOException, MalformedFileException {
        setFile(file, ">");
    }

    @Override
    public void setFile(Path file, String headerIdentifier) throws IOException, MalformedFileException {
        cache.accept(parser.parse(file, headerIdentifier));
        indexHolder.indexFileIntoBlocks(file, headerIdentifier);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Protein retrieveFromStore(String key) throws InvalidArgumentException, StoreAccessException {
        Protein retrievedProtein;
        try {
            if ((retrievedProtein = cache.retrieve(key)) == null){
                retrievedProtein = indexHolder.readBlock(key);
            }
            return retrievedProtein;
        } catch (IOException e) {
            StoreAccessException aex = new StoreAccessException("could not read from fasta");
            aex.initCause(aex);
            throw aex;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Protein> retrieveMultipleFromStore(Collection<String> keys) throws InvalidArgumentException, StoreAccessException {
        List<Protein> proteinList = new ArrayList<>(keys.size());
        for (String aKey : keys) {
            proteinList.add(retrieveFromStore(aKey));
        }
        return proteinList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToStore(Protein entry) throws StoreAccessException {
        throw new UnsupportedOperationException("lawl");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMultipleToStore(Collection<? extends Protein> entryCollection) throws StoreAccessException {
        throw new UnsupportedOperationException("multi-lawl");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchStorageStrategies(StoreStrategy<String, Protein> newStorageStrategy) throws StoreAccessException {
        try {
            newStorageStrategy.accept(retrieveAllFromStore());
        } catch (InvalidArgumentException e) {
            StoreAccessException ex = new StoreAccessException("could not retrieve sequence for header supposedly present in fasta");
            ex.initCause(e);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean storeContains(String accession) throws StoreAccessException, InvalidArgumentException {
        return retrieveFromStore(accession) != null;
    }

    @Override
    public Collection<Protein> retrieveAllFromStore() throws StoreAccessException, InvalidArgumentException {
        return retrieveMultipleFromStore(this.indexHolder.getIndex().keySet());
    }
}
