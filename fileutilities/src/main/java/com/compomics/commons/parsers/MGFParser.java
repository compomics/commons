package com.compomics.commons.parsers;

import com.compomics.commons.generalutilities.RegexUtilities;
import com.compomics.commons.model.Peak;
import com.compomics.commons.model.Range;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.model.Spectrum;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class MGFParser {

    private static final Pattern commaPattern = Pattern.compile(",");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    private static final Pattern chargePattern = Pattern.compile(" and ");


    /**
     * indexes an mgf file into spectra
     *
     * @param file           the file to index
     * @param blockSeparator the separator for each block, usually this would be BEGIN IONS as we start each block with the title line
     * @throws MalformedFileException if the file could not properly be indexed
     * @throws IOException            if there was a problem reading from the file
     */
    public List<Spectrum> parse(Path file, String blockSeparator) throws MalformedFileException, IOException {
        Spectrum spectrumToAdd = null;

        List<Spectrum> parsedSpectra = new ArrayList<>();
        List<Peak> peaksToAddToSpectrum = new ArrayList<>();

        LineNumberReader reader = new LineNumberReader(new FileReader(file.toFile()));

        String s = reader.readLine();

        try {
            while (s != null) {
                if (s.toUpperCase().contains(blockSeparator.toUpperCase())) {
                    if (spectrumToAdd != null) {
                        spectrumToAdd.addPeaks(peaksToAddToSpectrum);
                        peaksToAddToSpectrum.clear();
                        parsedSpectra.add(spectrumToAdd);
                    }
                    spectrumToAdd = new Spectrum();
                }
                if (s.toUpperCase().startsWith("TITLE")) {
                    spectrumToAdd.setTitle(URLDecoder.decode(s.substring(s.indexOf("TITLE=") + 6), "UTF-8"));
                } else if (s.toUpperCase().startsWith("PEPMASS=")) {
                    String[] pepmasses = s.substring(s.indexOf("PEPMASS=") + 8).split("\\s");
                    spectrumToAdd.setPrecursor(Double.parseDouble(pepmasses[0]));
                    if (pepmasses.length > 1) {
                        spectrumToAdd.setPrecursorIntensity(Double.parseDouble(pepmasses[1]));
                    } else {
                        spectrumToAdd.setPrecursorIntensity(new Double(0.0));
                    }
                } else if (s.startsWith("RTINSECONDS=")) {
                    try {
                        Matcher matcher = RegexUtilities.doublePattern.matcher(s);
                        if (matcher.find()) {
                            Range retentionRange = new Range(Double.parseDouble(matcher.toMatchResult().group()), null);
                            if (s.contains("-") && matcher.find()) {
                                retentionRange.setEnd(Double.parseDouble(matcher.group()));
                            }
                            spectrumToAdd.setRetentionTime(retentionRange);
                        }
                    } catch (NumberFormatException e) {
                        MalformedFileException ex = new MalformedFileException("could not parse double: " + s);
                        ex.initCause(e);
                        throw ex;
                    }
                } else if (s.startsWith("CHARGE=")) {
                    for (String aCharge : commaPattern.split(chargePattern.matcher(s.substring(s.indexOf("CHARGE=") + 7)).replaceAll(""))) {
                        if (aCharge.endsWith("+") || !aCharge.equalsIgnoreCase("Mr")) {
                            spectrumToAdd.addCharge(Integer.parseInt(String.valueOf(aCharge.charAt(0))));
                        } else if (aCharge.endsWith("-")) {
                            spectrumToAdd.addCharge(-Integer.parseInt(String.valueOf(aCharge.charAt(0))));
                        }
                    }
                    if (spectrumToAdd.getCharges().isEmpty()) {
                        spectrumToAdd.addCharge(1);
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
                else if (s.startsWith("SCANS=")) {
                    try {
                        Matcher matcher = numberPattern.matcher(s);
                        while (matcher.find()) {
                            if (!matcher.group().isEmpty()) {
                                spectrumToAdd.addScanNumber(Integer.parseInt(matcher.group()));
                            }
                        }
                        if (spectrumToAdd.getScanNumbers().isEmpty()) {
                            throw new IllegalArgumentException("Cannot parse scan number: " + s + "\nreason: no scan number found");
                        }

                    } catch (NumberFormatException | IllegalStateException e) {
                        throw new MalformedFileException("Cannot parse scan number: " + s);
                    }
                } else {
                    Matcher matcher = RegexUtilities.peakPattern.matcher(s);
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
                            peaksToAddToSpectrum.add(currentPeak);
                        } catch (NumberFormatException e) {
                            MalformedFileException ex = new MalformedFileException("could not parse a peak set\nline was " + s);
                            ex.initCause(e);
                            throw ex;
                        }
                    }
                }
                s = reader.readLine();
            }

        } catch (NullPointerException | UnsupportedEncodingException e) {
            //if null pointer happens the general order of the file is not managed or the starting line for spectra is not correct
            MalformedFileException ex = new MalformedFileException("could not read mgf file\nstarting line of spectra is: " + blockSeparator);
            ex.initCause(e);
            throw ex;
        }
        return parsedSpectra;
    }


//
//    else if(line.startsWith("TAG"))
//
//    {
//        // sequence tag not implemented
//    }
//
//    else if(line.startsWith("RAWSCANS"))
//
//    {
//        // raw scans not implemented
//    }

}
