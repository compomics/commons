package com.compomics.commons.parsers.Obo;

import com.compomics.commons.model.Ontology.OntologyTerm;
import org.junit.BeforeClass;

import java.io.File;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 4/19/2016.
 */
public class OboParserTest {

    static File oboFile;

    @BeforeClass
    public static void setupClass(){
        oboFile = new File(OboParserTest.class.getClassLoader().getResource("psi-peff.obo").getFile());

    }

    @org.junit.Test
    public void testParseOboFile() throws Exception {

        Map<String,OntologyTerm> result = OboParser.parseOboFile(oboFile.toPath());
        assertThat(result.size(),equalTo(45));
        assertThat(result.values().stream().filter(e -> e.getAcceptedClass() != null).count(),equalTo(42L));
    }
}