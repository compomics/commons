package com.compomics.commons.model;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class Peak {

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
}
