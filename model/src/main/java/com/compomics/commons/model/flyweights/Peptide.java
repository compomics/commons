package com.compomics.commons.model.flyweights;

/**
 * Created by Davy Maddelein on 30/04/2015.
 */
public class Peptide {

private String sequence;


    public Peptide(String aSequence){
        this.sequence = aSequence;
    }

    public String getSequence(){
        return sequence;

    }
}
