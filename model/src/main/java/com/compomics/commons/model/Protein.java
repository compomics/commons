package com.compomics.commons.model;

import com.compomics.commons.model.flyweights.Peptide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Davy Maddelein on 30/04/2015.
 */
public class Protein extends com.compomics.commons.model.flyweights.Protein {

    List<com.compomics.commons.model.flyweights.Peptide> linkedPeptides = new ArrayList<>();

    public Protein(String aHeader) {
        super(aHeader);
    }

    public Protein(String aHeader, String aSequence) {
        super(aHeader, aSequence);
    }

    public void addPeptide(Peptide aPeptide){
        linkedPeptides.add(aPeptide);
    }

    public void addPeptides(Collection<? extends Peptide> peptides){
        linkedPeptides.addAll(peptides);
    }

    public List<Peptide> getPeptides(){
        return Collections.unmodifiableList(linkedPeptides);
    }

}
