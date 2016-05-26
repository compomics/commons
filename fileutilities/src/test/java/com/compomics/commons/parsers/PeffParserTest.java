package com.compomics.commons.parsers;

import com.compomics.commons.model.standards.peff.PeffProtein;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 4/22/2016.
 */
public class PeffParserTest {

    static File peffFile;

    @BeforeClass
    public static void initPeffFile() throws IOException {
        //todo move to fasta test suite
            peffFile = new ClassPathResource("SmallTestDB.peff").getFile();
    }

    @Test
    public void testParse() throws Exception {
        PeffParser testParser = new PeffParser();

        List<PeffProtein> test = testParser.parse(peffFile.toPath(),">");

    }
}