package com.compomics.commons.model;

import com.compomics.commons.model.flyweights.AminoAcid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davy Maddelein on 30/04/2015.
 */
public class Modification {

    private String name;
    private double weightDifference;
    private List<AminoAcid> targettableAA = new ArrayList<>(20);
    private String shorthand;

    public Modification(String name, double weightDifference, List<AminoAcid> targettableAA) {
        this.name = name;
        this.weightDifference = weightDifference;
        this.targettableAA = targettableAA;
        this.shorthand = name;
    }

    public String getName() {
        return name;
    }

    public double getWeightDifference() {
        return weightDifference;
    }

    public List<AminoAcid> getTargettableAA() {
        return targettableAA;
    }

    public String getShorthand() {
        return shorthand;
    }

    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }
}
