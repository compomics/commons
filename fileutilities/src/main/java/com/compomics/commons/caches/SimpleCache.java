package com.compomics.commons.caches;

import com.compomics.commons.interfaces.StoreStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davy Maddelein on 06/05/2015.
 */
public class SimpleCache<T,U> implements StoreStrategy<T,U> {

    private int maxCacheSize = 1000;
    private List<U> listCache;

    public SimpleCache(){
        listCache = new ArrayList<>(maxCacheSize);
    }


    public SimpleCache(int maxCacheSize){
        this.maxCacheSize = maxCacheSize;
        listCache = new ArrayList<>(maxCacheSize);
    }

    @Override
    public boolean accept(U anEntry) {
        return listCache.size() +1 < maxCacheSize && listCache.add(anEntry);
    }

    @Override
    public boolean accept(Collection<? extends U> entries) {
        if (listCache.size()+entries.size() < maxCacheSize){
            boolean success = true;
            for (U entry : entries){
                success = this.accept(entry);
                if (!success){
                    //report partial failure
                }
            }
            return success;
        }
        return false;
    }

    @Override
    public U retrieve(T identifier) {
        return listCache.stream().filter(e -> e.toString().equals(identifier)).findAny().get();
    }

    @Override
    public Collection<? extends U> retrieveAll() {
        return Collections.unmodifiableCollection(listCache);
    }

    @Override
    public Collection<? extends U> retrieveSubSet(Collection<? extends T> identifiers) {
        return Collections.unmodifiableCollection(listCache.stream().filter(e -> identifiers.contains(e.toString())).collect(Collectors.toList()));
    }

    @Override
    public boolean purge(T identifierToPurgeFromStore) {
        return listCache.remove(retrieve(identifierToPurgeFromStore));
    }

    @Override
    public boolean purgeSubset(Collection<? extends T> identifiersToPurgeFromStore) {
        //todo implement this better once we have decided on more implementation details
        boolean success = false;
        for (T anIdentifier : identifiersToPurgeFromStore){
            success = purge(anIdentifier);
            if (!success){
                //report partial error?
            }
        }
        return success;
    }

    @Override
    public boolean purgeAll() {
        listCache.clear();
        return listCache.size() == 0;
    }
}
