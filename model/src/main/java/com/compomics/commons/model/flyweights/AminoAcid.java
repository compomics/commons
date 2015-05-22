package com.compomics.commons.model.flyweights;

/**
 * Created by Davy Maddelein on 29/04/2015.
 */
public class AminoAcid {
    
    private String name;
    private Character oneLetterCode;
    private String threeLetterCode;

    public AminoAcid(String name, Character oneLetterCode, String threeLetterCode) {
        this.name = name;
        this.oneLetterCode = oneLetterCode;
        this.threeLetterCode = threeLetterCode;
    }

    public String getName() {
        return name;
    }

    public Character getOneLetterCode() {
        return oneLetterCode;
    }

    public String getThreeLetterCode() {
        return threeLetterCode;
    }
}
