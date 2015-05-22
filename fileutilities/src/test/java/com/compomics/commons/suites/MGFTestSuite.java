package com.compomics.commons.suites;

import com.compomics.commons.parsers.MGFParserTest;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * Created by Davy Maddelein on 18/05/2015.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses(MGFParserTest.class)


public class MGFTestSuite {

    public static File MGFFile;

    @ClassRule
    public static ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            MGFFile = new ClassPathResource("93365bd9f04c7ece___E06162_1t_20_4268_BSA_250fmol.raw.-1.mgf").getFile();
        }

};
}
