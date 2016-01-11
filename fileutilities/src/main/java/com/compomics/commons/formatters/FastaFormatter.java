package com.compomics.commons.formatters;

import com.compomics.commons.interfaces.FileFormatter;
import com.compomics.commons.model.Protein;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Davy Maddelein on 05/10/2015.
 */
public class FastaFormatter implements FileFormatter<String, Protein> {

    @Override
    public String toFormat(Protein out) {
        StringBuilder builder = new StringBuilder("");
        builder.append(out.getHeader()).append("\n");
        builder.append(out.getSequence());
        //todo add sequence formatting
        return builder.toString();
    }

    @Override
    public Protein fromFormat(String in) throws UnsupportedEncodingException, MalformedFileException {

        String[] split = in.split("\n");
        if (split.length > 1 || split[0].startsWith(">")) {
            Protein out = new Protein(split[0]);
            StringBuilder sequence = new StringBuilder("");
            for (int i = 1; i < split.length; i++) {
                sequence.append(split[i]);
            }
            out.setSequence(sequence.toString());
            return out;
        } else {
            throw new MalformedFileException("header not properly formatted");
        }
    }
}
