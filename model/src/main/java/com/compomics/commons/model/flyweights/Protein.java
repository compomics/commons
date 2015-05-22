package com.compomics.commons.model.flyweights;

/**
 * Created by Davy Maddelein on 23/04/2015.
 */
public class Protein {

    private String header;
    private String sequence;

    public Protein(String aHeader){
        header = aHeader;
    }

    public Protein (String aHeader,String aSequence){
        header = aHeader;
        sequence = aSequence;
    }

    public String getHeader() {
        return header;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
