package com.compomics.commons.datastrategies;

import com.compomics.commons.interfaces.DecoyStrategy;
import com.compomics.commons.model.flyweights.Protein;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 27/04/2015.
 */
public class DecoyStrategyTest {


    // dummy implementation
    DecoyStrategy testStrategy = new DecoyStrategy() {
        @Override
        public String produceDecoySequence(Protein aProtein) {
            return DecoyStrategy.super.produceDecoySequence(aProtein);
        }

        @Override
        public String produceDecoySequence(String aSequence) {
            return DecoyStrategy.super.produceDecoySequence(aSequence);
        }
    };

    Protein stubProtein = new Protein("", "abcdefghijklmnopqrstuvwxyz");

    @Test
    public void testProduceDecoySequence() throws Exception {

        String result = testStrategy.produceDecoySequence(stubProtein);
        assertThat(result,is(equalTo((new StringBuilder(stubProtein.getSequence()).reverse().toString()))));

    }

    @Test
    public void testProduceDecoySequence1() throws Exception {

    }
}