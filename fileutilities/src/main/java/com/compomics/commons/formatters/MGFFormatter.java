package com.compomics.commons.formatters;

import com.compomics.commons.model.Spectrum;

import java.util.stream.Collectors;

/**
 * Created by Davy Maddelein on 08/05/2015.
 */
public class MGFFormatter {

    public static String toFormat(Spectrum aSpectrum) {

        StringBuilder builder = new StringBuilder().append("BEGIN IONS").append(System.lineSeparator());

        builder.append("TITLE=")
                .append(aSpectrum.getTitle())
                .append(System.lineSeparator());

        builder.append("PEPMASS=").append(aSpectrum.getPrecursor())
                .append(aSpectrum.getPrecursorIntensity() != 0.0
                        ? " " + aSpectrum.getPrecursorIntensity() + System.lineSeparator()
                        : System.lineSeparator());

        builder.append("CHARGE=")
                .append(aSpectrum.getCharges().stream()
                        .map(e -> e > 0 ? String.valueOf(e) + "+" : String.valueOf(e) + "-")
                        .collect(Collectors.joining(",")))
                .append(System.lineSeparator());

        builder.append("RTINSECONDS=").append(aSpectrum.getRetentionTime().getStart())
                .append(!aSpectrum.getRetentionTime().getEnd().equals(0.0)
                        ? " " + aSpectrum.getRetentionTime().getEnd() + System.lineSeparator()
                        : System.lineSeparator());

        builder.append("SCANS=")
                .append(aSpectrum.getScanNumbers())
                .append(System.lineSeparator());

        builder.append(aSpectrum.getPeaks().stream()
                .map(e -> String.valueOf(e.getMZ()) + "\t" + String.valueOf(e.getIntensity()))
                .collect(Collectors.joining(System.lineSeparator())));

        builder.append("END IONS").append(System.lineSeparator());

        return builder.toString();
    }
}
