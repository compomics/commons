package com.compomics.commons.formatters;

import com.compomics.commons.generalutilities.RegexUtilities;
import com.compomics.commons.model.Peak;
import com.compomics.commons.model.Range;
import com.compomics.commons.model.Spectrum;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Davy Maddelein on 08/05/2015.
 */
public class MGFFormatter {


    private static final Pattern commaPattern = Pattern.compile(",");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    private static final Pattern chargePattern = Pattern.compile(" and ");

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

        builder.append(aSpectrum.getPeaks().stream().sorted()
                .map(e -> String.valueOf(e.getMZ()) + "\t" + String.valueOf(e.getIntensity()))
                .collect(Collectors.joining(System.lineSeparator())));

        builder.append("END IONS").append(System.lineSeparator());

        return builder.toString();
    }

    public static Spectrum fromFormat(String MGFTextBlock) throws UnsupportedEncodingException, MalformedFileException {
        Spectrum readSpectrum = new Spectrum();

        String[] splitMGFBlock = MGFTextBlock.split("\n");

        for (String s : splitMGFBlock) {
            if (s.toUpperCase().startsWith("TITLE")) {
                readSpectrum.setTitle(URLDecoder.decode(s.substring(s.indexOf("TITLE=") + 6), "UTF-8"));
            } else if (s.toUpperCase().startsWith("PEPMASS=")) {
                String[] pepmasses = s.substring(s.indexOf("PEPMASS=") + 8).split("\\s");
                readSpectrum.setPrecursor(Double.parseDouble(pepmasses[0]));

                if (pepmasses.length > 1) {
                    readSpectrum.setPrecursorIntensity(Double.parseDouble(pepmasses[1]));
                } else {
                    readSpectrum.setPrecursorIntensity(0.0);
                }

            } else if (s.startsWith("RTINSECONDS=")) {
                try {
                    Matcher matcher = RegexUtilities.doublePattern.matcher(s);

                    if (matcher.find()) {
                        Range retentionRange = new Range(Double.parseDouble(matcher.toMatchResult().group()), null);

                        if (s.contains("-") && matcher.find()) {
                            retentionRange.setEnd(Double.parseDouble(matcher.group()));
                        }
                        readSpectrum.setRetentionTime(retentionRange);
                    }
                } catch (NumberFormatException e) {
                    MalformedFileException ex = new MalformedFileException("could not parse double: " + s);
                    ex.initCause(e);
                    throw ex;
                }

            } else if (s.startsWith("CHARGE=")) {
                for (String aCharge : commaPattern.split(chargePattern.matcher(s.substring(s.indexOf("CHARGE=") + 7)).replaceAll(""))) {
                    if (aCharge.endsWith("+") || !aCharge.equalsIgnoreCase("Mr")) {
                        readSpectrum.addCharge(Integer.parseInt(String.valueOf(aCharge.charAt(0))));
                    } else if (aCharge.endsWith("-")) {
                        readSpectrum.addCharge(-Integer.parseInt(String.valueOf(aCharge.charAt(0))));
                    }
                }

                if (readSpectrum.getCharges().isEmpty()) {
                    readSpectrum.addCharge(1);
                }
            } else if (s.startsWith("SCANS=")) {
                try {
                    Matcher matcher = numberPattern.matcher(s);
                    while (matcher.find()) {

                        if (!matcher.group().isEmpty()) {
                            readSpectrum.addScanNumber(Integer.parseInt(matcher.group()));
                        }
                    }

                    if (readSpectrum.getScanNumbers().isEmpty()) {
                        throw new IllegalArgumentException("Cannot parse scan number: " + splitMGFBlock + "\nreason: no scan number found");
                    }

                } catch (NumberFormatException | IllegalStateException e) {
                    throw new MalformedFileException("Cannot parse scan number: " + splitMGFBlock);
                }
            } else {
                Matcher matcher = RegexUtilities.MGFPeakPattern.matcher(s);
                if (matcher.find()) {
                    matcher = RegexUtilities.doublePattern.matcher(s);
                    Peak currentPeak = new Peak();
                    try {
                        if (matcher.find()) {
                            currentPeak.setMZ(Double.parseDouble(matcher.group()));
                        }
                        if (matcher.find()) {
                            currentPeak.setIntensity(Double.parseDouble(matcher.group()));
                        }
                        readSpectrum.addPeak(currentPeak);
                    } catch (NumberFormatException e) {
                        MalformedFileException ex = new MalformedFileException("could not parse a peak set\nline was " + splitMGFBlock);
                        ex.initCause(e);
                        throw ex;
                    }
                }
            }
        }
        return readSpectrum;
    }
}

//                else if(line.startsWith("TOLU"))
//
//                {
//                    // peptide tolerance unit not implemented
//                }
//
//                else if(line.startsWith("TOL"))
//
//                {
//                    // peptide tolerance not implemented
//                }
//
//                else if(line.startsWith("SEQ"))
//
//                {
//                    // sequence qualifier not implemented
//                }
//
//                else if(line.startsWith("COMP"))
//
//                {
//                    // composition qualifier not implemented
//                }
//
//                else if(line.startsWith("ETAG"))
//
//                {
//                    // error tolerant search sequence tag not implemented
//                }
//
//                else if(line.startsWith("TAG"))
//
//                {
//                    // sequence tag not implemented
//                }
//
//
//    else if(line.startsWith("RAWSCANS"))
//
//    {
//        // raw scans not implemented
//    }

