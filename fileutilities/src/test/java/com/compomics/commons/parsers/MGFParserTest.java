package com.compomics.commons.parsers;

import com.compomics.commons.model.Spectrum;
import com.compomics.commons.suites.MGFTestSuite;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 11/05/2015.
 */
public class MGFParserTest {

    MGFParser parser = new MGFParser();

    @Test
    public void testParse() throws Exception {
        List<Spectrum> testSpectra = parser.parse(MGFTestSuite.MGFFile.toPath(), "BEGIN IONS");

        //general tests
        assertThat(testSpectra.size(), is(equalTo(6910)));


        //random data specific tests

        //simple spectrum

        //charges
        assertThat(testSpectra.get(42).getCharges().isEmpty(),is(false));
        assertThat(testSpectra.get(42).getCharges().size(),is(1));
        assertThat(testSpectra.get(42).getCharges().get(0),is(2));

        //scans

        assertThat(testSpectra.get(42).getScanNumbers().isEmpty(),is(false));
        assertThat(testSpectra.get(42).getScanNumbers().size(),is(1));
        assertThat(testSpectra.get(42).getScanNumbers().get(0),is(2253));

        //retention times

        assertThat(testSpectra.get(42).getRetentionTime(),is(notNullValue()));
        assertThat(testSpectra.get(42).getRetentionTime().getStart(),is(805.93152));
        assertThat(testSpectra.get(42).getRetentionTime().getEnd(),is(nullValue()));

        //masses

        assertThat(testSpectra.get(42).getPrecursor(),is(1021.7891));
        assertThat(testSpectra.get(42).getPrecursorIntensity(),is(48952.184));

        //peaks

        assertThat(testSpectra.get(42).getPeaks(),is(notNullValue()));
        assertThat(testSpectra.get(42).getPeaks().size(),is((16)));
        //just check a couple
        assertThat(testSpectra.get(42).getPeaks().get(0),is((notNullValue())));
        assertThat(testSpectra.get(42).getPeaks().get(0).getMZ(),is((142.96431)));
        assertThat(testSpectra.get(42).getPeaks().get(0).getIntensity(),is((47.643492)));

        assertThat(testSpectra.get(42).getPeaks().get(5),is((notNullValue())));
        assertThat(testSpectra.get(42).getPeaks().get(5).getMZ(),is((217.06248)));
        assertThat(testSpectra.get(42).getPeaks().get(5).getIntensity(),is((57.175145)));


        assertThat(testSpectra.get(128).getCharges().isEmpty(),is(false));
        assertThat(testSpectra.get(128).getCharges().size(),is(1));
        assertThat(testSpectra.get(128).getCharges().get(0),is(3));

        //retention times

        assertThat(testSpectra.get(128).getRetentionTime(),is(notNullValue()));
        assertThat(testSpectra.get(128).getRetentionTime().getStart(),is(898.48452));
        assertThat(testSpectra.get(128).getRetentionTime().getEnd(),is(notNullValue()));
        assertThat(testSpectra.get(128).getRetentionTime().getEnd(),is(900.3303));


        //masses

        assertThat(testSpectra.get(128).getPrecursor(),is(516.55376));
        assertThat(testSpectra.get(128).getPrecursorIntensity(),is(26332.839));

        //peaks

        assertThat(testSpectra.get(128).getPeaks(),is(notNullValue()));
        assertThat(testSpectra.get(128).getPeaks().size(),is((18)));
        //just check a couple
        assertThat(testSpectra.get(128).getPeaks().get(0),is((notNullValue())));
        assertThat(testSpectra.get(128).getPeaks().get(0).getMZ(),is((200.97988)));
        assertThat(testSpectra.get(128).getPeaks().get(0).getIntensity(),is((36.720863)));

        assertThat(testSpectra.get(128).getPeaks().get(5),is((notNullValue())));
        assertThat(testSpectra.get(128).getPeaks().get(5).getMZ(),is((316.67017)));
        assertThat(testSpectra.get(128).getPeaks().get(5).getIntensity(),is((50.19924)));

        //scans

        assertThat(testSpectra.get(128).getScanNumbers().isEmpty(),is(false));
        assertThat(testSpectra.get(128).getScanNumbers().size(),is(2));
        assertThat(testSpectra.get(128).getScanNumbers().get(0),is(2692));
        assertThat(testSpectra.get(128).getScanNumbers().get(1),is(2703));



    }
}