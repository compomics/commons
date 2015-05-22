package com.compomics.commons.generalutilities;

import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 20/05/2015.
 */
public class RegexUtilities {

    public static final Pattern doublePattern = Pattern.compile("(\\d*\\.\\d*)");
    public static final Pattern peakPattern = Pattern.compile(doublePattern.pattern()+"\\t"+doublePattern.pattern());

}