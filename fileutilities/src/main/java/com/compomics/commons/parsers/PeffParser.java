package com.compomics.commons.parsers;

import com.compomics.commons.model.Ontology.specificobotypes.PeffOntologyTerm;
import com.compomics.commons.model.exceptions.MalformedFileException;
import com.compomics.commons.model.flyweights.Protein;
import com.compomics.commons.model.standards.peff.Header;
import com.compomics.commons.model.standards.peff.PeffProtein;
import com.compomics.commons.parsers.iterators.FastaFileIterator;
import com.compomics.commons.parsers.obo.additionalrules.PeffOboParser;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Davy Maddelein on 4/20/2016.
 */
public class PeffParser extends FastaParser {

   Map<String,PeffOntologyTerm> peffSpecification = new HashMap<>();

    PeffOboParser<PeffOntologyTerm> oboParser = new PeffOboParser<>();

    //todo fix to specific exception
    public PeffParser() throws Exception{
        try{
            peffSpecification = oboParser.parseOboFile(new ClassPathResource("psi-peff.obo").getFile().toPath());
        } catch (Exception e){
            //stuff
            e.printStackTrace();
        }
    }

    @Override
    public List<PeffProtein> parse(Path file, String blockSeparator) throws IOException, MalformedFileException {
        List<PeffProtein> returnList = new ArrayList<>();
        FastaFileIterator iter = new FastaFileIterator(file,blockSeparator);
        if (peffSpecification.isEmpty()) {
            iter.forEachRemaining(e -> returnList.add(new PeffProtein(e.getHeader(), e.getSequence())));
            return returnList;
        } else {
            while(iter.hasNext()){
                Protein protein = iter.next();

                String[] information = protein.getHeader().split("\\\\");


                Header peffHeader = new Header(information[0].substring(information[0].indexOf(blockSeparator)+1,information[0].indexOf("\\")).trim());

                Map<String,Object> extraInformation = new HashMap<>();

                for (int i = 1; i < information.length; i++) {
                    String[] keyValue = information[i].split("=");
                    if(peffSpecification.containsKey(keyValue[0])){
                        extraInformation.put(keyValue[0],peffSpecification.get(keyValue[0]).getAcceptedClass().cast(keyValue[1]));
                    }
                }
                peffHeader.setAdditionalInformation(extraInformation);
                returnList.add(new PeffProtein(peffHeader,protein.getSequence()));
            }
        }
    return returnList;
    }
}
