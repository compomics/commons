package com.compomics.commons.datastrategies.decoystrategies.specificimplementations;

import com.compomics.commons.datastrategies.decoystrategies.RewardDecoyStrategy;
import com.compomics.commons.csharpadapters.CSharpStringHash;

/**
 * class designed to emulate the reward decoy strategy in Max Quant as close as possible
 * Created by Davy Maddelein on 27/04/2015.
 */
public class MaxQuantRewardDecoyStrategy extends RewardDecoyStrategy {

    @Override
    public String produceDecoySequence(String aSequence) {
        StringBuilder decoySequence = new StringBuilder(aSequence.length());
        if ((CSharpStringHash.getHash(aSequence) / 2) % 2 == 1) {

            return decoySequence.append(
                    aSequence.substring((int) Math.ceil(aSequence.length() / 2), aSequence.length()))
                    .reverse()
                    .append(aSequence.substring(0, (int) Math.ceil(aSequence.length()))).toString();
        } else {

            decoySequence.append(aSequence.substring(0, (int) Math.ceil(aSequence.length() / 2)));
            return decoySequence.append(
                    new StringBuilder(aSequence.substring((int) Math.ceil(aSequence.length() / 2)))
                            .reverse()
                            .toString())
                    .toString();
        }
    }
}

