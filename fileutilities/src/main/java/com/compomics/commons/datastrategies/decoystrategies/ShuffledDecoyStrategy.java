package com.compomics.commons.datastrategies.decoystrategies;

import com.compomics.commons.interfaces.DecoyStrategy;
import com.compomics.commons.generalutilities.StringUtilities;
import com.compomics.commons.model.flyweights.Protein;

import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class ShuffledDecoyStrategy implements DecoyStrategy {


    private Random randomToUse;

    @Override
    public String produceDecoySequence(Protein aProtein) {
        return produceDecoySequence(aProtein.getSequence());
    }

    @Override
    public String produceDecoySequence(String aSequence) {
       StringBuilder builder =  new StringBuilder(aSequence.length());
        if (randomToUse == null){
            List<Character> chars = StringUtilities.getListBackedByStringChars(aSequence);
            Collections.shuffle(chars);
            chars.stream().forEach(builder::append);
        } else {
            List<Character> chars = StringUtilities.getListBackedByStringChars(aSequence);
            Collections.shuffle(chars,randomToUse);
            chars.stream().forEach(builder::append);
        }
        return builder.toString();

    }

    public void setRandomToUse(Random randomToUse) {
        this.randomToUse = randomToUse;
    }
}
