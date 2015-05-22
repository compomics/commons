/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.commons.interfaces;


import com.compomics.commons.model.exceptions.InvalidArgumentException;
import com.compomics.commons.model.exceptions.StoreAccessException;

import java.util.Collection;

/**
 * the general contract that an information storage manager must fulfill
 * Created by Davy Maddelein on 18/02/2015.
 */
public interface StoreManager<T, U> {

    /**
     * retrieves the entry identified by the given key from the underlying storage manager
     *
     * @param key the entry key that identifies the entry for retrieval
     * @return the requested object
     */
    U retrieveFromStore(T key) throws InvalidArgumentException, StoreAccessException;

    /**
     * retrieves multiple entries from the store identified by the passed collection of identifying accessions
     *
     * @param keys the accessions to retrieve the entries for
     * @return a {@link java.util.Collection} of the requested entries
     */
    Collection<U> retrieveMultipleFromStore(Collection<T> keys) throws InvalidArgumentException, StoreAccessException;

    /**
     * add the passed entry to the underlying storage manager
     *
     * @param entry the entry to store
     */
    void addToStore(U entry) throws StoreAccessException;

    /**
     * adds a collection of entries to the underlying storage manager
     *
     * @param entryCollection the entries to add to the storage manager
     */
    void addMultipleToStore(Collection<? extends U> entryCollection) throws StoreAccessException;

    /**
     * switches out the current storage strategy with the one passed to this method, depending on the implementations of both strategies, this might take a while and if needed should be called in a separate thread
     *
     * @param newStorageStrategy the storage strategy to replace the current one with
     */
    void switchStorageStrategies(StoreStrategy<T, U> newStorageStrategy) throws StoreAccessException;

    /**
     * checks if an entry identified by the given accession is already present in the store
     * @param accession the accession to check the store content for
     * @return true if present, false otherwise
     */
    boolean storeContains(T accession) throws StoreAccessException, InvalidArgumentException;

    Collection<U> retrieveAllFromStore() throws StoreAccessException, InvalidArgumentException;
}
