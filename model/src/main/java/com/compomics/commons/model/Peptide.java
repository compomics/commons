package com.compomics.commons.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Davy Maddelein on 30/04/2015.
 */
public class Peptide {

    private String sequence;

    private List<Modification> modifications = new ArrayList<>();

    private List<Spectrum> identifiedBy = new ArrayList<>();


    public Peptide(String sequence){
        this.sequence = sequence;
    }

    public List<Modification> getModifications() {
        return Collections.unmodifiableList(modifications);
    }

    public void setModifications(List<Modification> modifications) {
        this.modifications.addAll(modifications);
    }

    public String getSequence() {
        return sequence;
    }

    public void addPSM(Spectrum aSpectrum){
        identifiedBy.add(aSpectrum);
    }

    public void addPSMs(List<? extends Spectrum> spectra){
        identifiedBy.addAll(spectra);
    }

    public List<Spectrum> getPSMs(){
        return Collections.unmodifiableList(identifiedBy);
    }
}
