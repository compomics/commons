package com.compomics.commons.model;

import java.util.Objects;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class Peak implements Comparable<Peak> {

    private Double MZ = 0.0;
    private Double intensity = 0.0;

    public Peak(){}

    public Peak(Double MZ, Double intensity) {
        this.MZ = MZ;
        this.intensity = intensity;
    }

    public Double getMZ() {
        return MZ;
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setMZ(Double MZ) {
        this.MZ = MZ;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peak peak = (Peak) o;
        return Objects.equals(MZ, peak.MZ) &&
                Objects.equals(intensity, peak.intensity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MZ, intensity);
    }

    @Override
    public int compareTo(Peak o) {
        return 0;

    }
}
