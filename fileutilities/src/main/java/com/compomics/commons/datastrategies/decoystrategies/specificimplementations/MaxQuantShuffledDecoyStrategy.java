package com.compomics.commons.datastrategies.decoystrategies.specificimplementations;

import com.compomics.commons.interfaces.DecoyStrategy;
import com.compomics.commons.csharpadapters.CSharpRandom;
import com.compomics.commons.generalutilities.ArrayUtilities;
import com.compomics.commons.model.flyweights.Protein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Davy Maddelein on 28/04/2015.
 */
public class MaxQuantShuffledDecoyStrategy implements DecoyStrategy {

    List<Character> cleavingAminoAcids = new ArrayList<>();

    CSharpRandom seededRandom = new CSharpRandom(9);

    public MaxQuantShuffledDecoyStrategy(Character... cleavingAminoAcids) {
        this.cleavingAminoAcids = Arrays.asList(cleavingAminoAcids);
    }

    @Override
    public String produceDecoySequence(Protein aProtein) {
        return produceDecoySequence(aProtein.getSequence());
    }

    @Override
    public String produceDecoySequence(String aSequence) {
        List<Integer> indexesToChange = new ArrayList<>();
        StringBuilder builder = new StringBuilder(aSequence.length());

        for (int index = 0; index < aSequence.length(); ++index) {
            int ch = aSequence.codePointAt(index);
            if (index != 0 || !this.cleavingAminoAcids.contains(ch)) {
                indexesToChange.add(index);
            }
        }
        int counter = 0;
        int[] numArray = nextPermutation(indexesToChange.size());
        for (int index = 0; index < aSequence.length(); ++index){
            if (index == 0 || this.cleavingAminoAcids.contains(aSequence.codePointAt(index))) {
                builder.append(aSequence.codePointAt(index));
            } else {
                builder.append(aSequence.codePointAt(numArray[counter]));
                counter++;
        }}
        return builder.toString();
    }

    private int[] nextPermutation(int n) {
        int[] numArray = ArrayUtilities.consecutiveInts(n);
        for (int minValue = 0; minValue < n; ++minValue) {
            int index = seededRandom.next(minValue, n);
            int num = numArray[minValue];
            numArray[minValue] = numArray[index];
            numArray[index] = num;
        }
        return numArray;
    }
}

