package com.compomics.commons.parsers;

import com.compomics.commons.interfaces.BlockReader;
import com.compomics.commons.interfaces.StoreManager;
import com.compomics.commons.interfaces.StoreStrategy;
import com.compomics.commons.model.Range;
import com.compomics.commons.model.Spectrum;
import com.compomics.commons.model.exceptions.InvalidArgumentException;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.model.exceptions.StoreAccessException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Created by Davy Maddelein on 05/05/2015.
 */
public class MzMLParser implements StoreManager<String, Spectrum>, BlockReader<Spectrum> {

    @Override
    public void indexFileIntoBlocks(Path file, String blockSeparator) throws MalformedFileException, IOException {

    }

    @Override
    public Spectrum readBlock(Range range) throws IOException {
        return null;
    }

    @Override
    public Spectrum retrieveFromStore(String key) throws InvalidArgumentException, StoreAccessException {
        return null;
    }

    @Override
    public Collection<Spectrum> retrieveMultipleFromStore(Collection<String> keys) throws InvalidArgumentException, StoreAccessException {
        return null;
    }

    @Override
    public void addToStore(Spectrum entry) throws StoreAccessException {

    }

    @Override
    public void addMultipleToStore(Collection<? extends Spectrum> entryCollection) throws StoreAccessException {

    }

    @Override
    public void switchStorageStrategies(StoreStrategy<String, Spectrum> newStorageStrategy) throws StoreAccessException {

    }

    @Override
    public boolean storeContains(String accession) throws StoreAccessException, InvalidArgumentException {
        return false;
    }

    @Override
    public Collection<Spectrum> retrieveAllFromStore() throws StoreAccessException, InvalidArgumentException {

        return null;
    }
}
