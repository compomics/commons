package com.compomics.commons.interfaces;

import com.compomics.commons.model.flyweights.Protein;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public interface DecoyStrategy {

    default String produceDecoySequence(Protein aProtein){
        return produceDecoySequence(aProtein.getSequence());
    }

    default String produceDecoySequence(String aSequence){
        return new StringBuilder(aSequence).reverse().toString();
    }

}
