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
}
