package com.compomics.commons.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class Spectrum {

    private String title;

    private List<Peak> peaks = new ArrayList<>();

    private List<Integer> scanNumbers = new ArrayList<>();

    private Range retentionTime;
    private Double precursor;
    private Double precursorIntensity;
    private List<Integer> charges = new ArrayList<>();

    public Spectrum(String title, List<Peak> peaks,Integer... charges) {
        this.title = title;
        this.peaks = peaks;
        Arrays.stream(charges).forEach(this.charges::add);
    }

    public Spectrum(String title,Integer... charges) {
        this.title = title;
        Arrays.stream(charges).forEach(this.charges::add);
    }

    public Spectrum() {
        
    }

    public String getTitle() {
        return title;
    }

    public List<Peak> getPeaks() {
        return peaks;
    }

    public Range getRetentionTime() {
        return retentionTime;
    }

    public void addPeaks(List<? extends Peak> peaks){
        this.peaks.addAll(peaks);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRetentionTime(Range retentionTime) {
        this.retentionTime = retentionTime;
    }

    public void addScanNumber(Integer i) {
        scanNumbers.add(i);
    }

    public void setPrecursor(Double v) {
        this.precursor = v;
    }

    public List<Integer> getScanNumbers() {
        return Collections.unmodifiableList(scanNumbers);
    }

    public Double getPrecursor() {
        return precursor;
    }


    public void setPrecursorIntensity(Double precursorIntensity) {
        this.precursorIntensity = precursorIntensity;
    }

    public Double getPrecursorIntensity() {
        return precursorIntensity;
    }

    public void addCharge(int c) {
        charges.add(c);
    }

    public List<Integer> getCharges(){
        return Collections.unmodifiableList(charges);
    }

    public void addPeak(Peak peak) {
        peaks.add(peak);
    }
}
