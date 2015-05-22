package com.compomics.commons.datastrategies.decoystrategies;

import com.compomics.commons.model.flyweights.Protein;
import org.junit.Before;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 24/04/2015.
 */
public class ShuffledDecoyStrategyTest {


    ShuffledDecoyStrategy test;

    Protein stubProtein = new Protein("","abcdefghijklmnopqrstuvwxyz");

    @Before
    public void generateNewDecoyStrategy(){
        test = new ShuffledDecoyStrategy();
    }

    @org.junit.Test
    public void testProduceDecoySequenceProtein() throws Exception {

        String testDecoy = test.produceDecoySequence(stubProtein);
        assertThat(testDecoy,is(not(equalTo(stubProtein.getSequence()))));
        Set<String> decoys = new HashSet<>(10);
        for (int i = 0; i < 10; i++){
            decoys.add(test.produceDecoySequence(stubProtein));
        }
        assertThat(decoys.size(),is(10));
    }

    @org.junit.Test
    public void testProduceDecoySequenceString() throws Exception {

        String testDecoy = test.produceDecoySequence(stubProtein.getSequence());
        assertThat(testDecoy,is(not(equalTo(stubProtein.getSequence()))));

    }

    //quite possibly not feasible to test, but you never know if inspiration hits
    @org.junit.Test
    public void testProduceDecoySequenceStringCustomRandom()  throws Exception{
        test.setRandomToUse(new Random());
        String testDecoy = test.produceDecoySequence(stubProtein.getSequence());
        assertThat(testDecoy, is(not(equalTo(stubProtein.getSequence()))));

    }
}