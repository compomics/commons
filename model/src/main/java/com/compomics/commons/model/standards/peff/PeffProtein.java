package com.compomics.commons.model.standards.peff;

import com.compomics.commons.model.flyweights.Protein;

/**
 * Created by Davy Maddelein on 4/19/2016.
 */
public class PeffProtein extends Protein {

    private Header fastaHeader;

    public PeffProtein (String unparsedHeader,String aSequence){
        super(unparsedHeader,aSequence);
    }

    public PeffProtein(Header aFastaHeader,String aSequence) {
            super(aFastaHeader.toString(),aSequence);
            this.fastaHeader = aFastaHeader;
        }

    public Header getFastaHeader() {
        return fastaHeader;
    }

    public PeffProtein setFastaHeader(Header parsedHeader){
        this.fastaHeader = parsedHeader;
        return this;
    }
}
