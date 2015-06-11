package com.compomics.commons.parsers;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.formatters.MGFFormatter;
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

        List<Spectrum> parsedSpectra = new ArrayList<>();

        LineReader reader = new LineReader(new FileReader(file.toFile()));

        String s = reader.readLine();

        try {
            StringBuilder builder = new StringBuilder();
            while (s != null) {
                if (s.toUpperCase().contains(blockSeparator.toUpperCase()) && builder.length() > 0) {
                    parsedSpectra.add(MGFFormatter.fromFormat(builder.toString()));
                    builder = new StringBuilder();
                }
                builder.append(s);
                s = reader.readLine();
            }

            if (builder.length() > 0) {
                parsedSpectra.add(MGFFormatter.fromFormat(builder.toString()));
            }

        } catch (NullPointerException | UnsupportedEncodingException e) {
            //if null pointer happens the general order of the file is not managed or the starting line for spectra is not correct
            MalformedFileException ex = new MalformedFileException("could not read mgf file\nstarting line of a spectrum was: " + blockSeparator);
            ex.initCause(e);
            throw ex;
        }
        return parsedSpectra;
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

}
