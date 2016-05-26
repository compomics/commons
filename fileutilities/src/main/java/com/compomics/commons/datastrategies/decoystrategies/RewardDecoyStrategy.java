package com.compomics.commons.datastrategies.decoystrategies;

import com.compomics.commons.interfaces.DecoyStrategy;
import com.compomics.commons.model.flyweights.Protein;


/**
 *
 * Created by Davy Maddelein on 27/04/2015.
 */
public class RewardDecoyStrategy implements DecoyStrategy {

    @Override
    public String produceDecoySequence(Protein aProtein) {
        return produceDecoySequence(aProtein.getSequence());
    }

    @Override
    public String produceDecoySequence(String aSequence) {
        StringBuilder decoySequence = new StringBuilder(aSequence.length());
        if ((aSequence.hashCode() / 2) % 2 == 1) {

            return decoySequence.append(
                    aSequence.substring((int) Math.ceil(aSequence.length() / 2d), aSequence.length()))
                    .reverse()
                    .append(aSequence.substring(0, (int) Math.ceil(aSequence.length()))).toString();
        } else {

            decoySequence.append(aSequence.substring(0, (int) Math.ceil(aSequence.length() / 2d)));
            return decoySequence.append(
                    new StringBuilder(aSequence.substring((int) Math.ceil(aSequence.length() / 2d)))
                            .reverse()
                            .toString())
                    .toString();
        }
    }
}
