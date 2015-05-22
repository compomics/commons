package com.compomics.commons.datamanagers.fastahandlers;

import com.compomics.commons.interfaces.DecoyStrategy;
import com.compomics.commons.interfaces.StoreStrategy;
import com.compomics.commons.model.exceptions.InvalidArgumentException;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.parsers.FastaParser;
import com.compomics.commons.interfaces.FileManager;
import com.compomics.commons.model.exceptions.StoreAccessException;
import com.compomics.commons.model.flyweights.Protein;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class FastaWithDecoyManager implements FileManager<String,Protein> {

    FastaParser originalFastaFile = new FastaParser();

    DecoyStrategy decoyStrategy;

    public FastaWithDecoyManager(FastaParser accessor, DecoyStrategy decoyStrategy){
        originalFastaFile = accessor;
        this.decoyStrategy = decoyStrategy;
    }
    @Override
    public void setFile(Path file) throws IOException, MalformedFileException {

    }

    @Override
    public void setFile(Path file, String headerIdentifier) throws IOException, MalformedFileException {

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
