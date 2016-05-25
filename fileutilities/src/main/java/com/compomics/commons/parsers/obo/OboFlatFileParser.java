package com.compomics.commons.parsers.obo;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.model.Ontology.BaseOntologyTerm;
import com.compomics.commons.model.Ontology.OntologyTermFactory;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 4/22/2016.
 */

public abstract class OboFlatFileParser<T extends BaseOntologyTerm> {

    private Pattern idPattern = Pattern.compile("id: [A-Z]*:[0-9]*\\n");
    private Pattern namePattern = Pattern.compile("name: .*\\n");
    private Pattern xrefPattern = Pattern.compile("(xref|xref_analog|xref_unk): .*$");

    private Pattern relationPattern = Pattern.compile("(is-a|has-a): [A-Z]*:[0-9]* ");

    private Pattern definitionPattern = Pattern.compile("\".*\"");

    private OntologyTermFactory<T> termFactory;


    public Set<T> parseOboFile(Path oboFile) throws IOException {

        StringBuilder termBlock = new StringBuilder();

        LineReader reader = new LineReader(new FileReader(oboFile.toFile()));
        String line = reader.readLine();

        Set<T> possibleHeaders = new HashSet<>();

        while (line != null) {
            //only need terms at the moment, can add default typedefs and instances later
            if (line.toLowerCase().contains("[term]")) {
                if (termBlock.length() != 0) {
                    try {
                        possibleHeaders.add(createBlock(termBlock.toString()));
                    }catch (MalformedFileException e) {
                        throw new IOException("could not parse provided file", e);
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                        //todo think of a proper exception to throw in this case
                    }
                    termBlock = new StringBuilder();
                }
            }
            termBlock.append(line);
            line = reader.readLine();
        }
        return possibleHeaders;
    }

    protected void additionalParsingRules(T termToExpand){};

    private T createBlock(String termblock) throws ReflectiveOperationException, MalformedFileException {

        Matcher ontologyTermIdMatcher = idPattern.matcher(termblock);

        boolean foundId = ontologyTermIdMatcher.find();
        //needed parts
         if (!foundId) {
            throw new MalformedFileException("missing needed information: ontology id");
        }

        T toReturn = getBaseTerm(ontologyTermIdMatcher.toMatchResult().group());

        //optional parts

        Matcher relationMatcher = relationPattern.matcher(termblock);

        while (relationMatcher.find()) {
            toReturn.addRelation(relationMatcher.group());
        }

        toReturn.getRelations().trimToSize();

        Matcher definitionMatcher = definitionPattern.matcher(termblock);

        while (definitionMatcher.find()) {
            if(!toReturn.getDefinition().isEmpty()){
                throw new MalformedFileException("more than one definition per ontology term block: definition already present was:" + toReturn.getDefinition() +" ,definition to add is: "+ definitionMatcher.group());
            }
            toReturn.addDefinition(definitionMatcher.group());
        }

        Matcher nameMatcher = namePattern.matcher(termblock);

        while (nameMatcher.find()) {
            if(!toReturn.getName().isEmpty()){
                throw new MalformedFileException("more than one name per ontology term block: definition already present was:" + toReturn.getName() +" ,definition to add is: "+ nameMatcher.group());
            }
            toReturn.setName(definitionMatcher.group());
        }

        Matcher xrefMatcher = xrefPattern.matcher(termblock);

        while (xrefMatcher.find()){
            toReturn.addXref(xrefMatcher.group());
        }

        toReturn.getXrefs().trimToSize();

        additionalParsingRules(toReturn);

        return toReturn;
    }

    protected T getBaseTerm(String ontologyID) throws ReflectiveOperationException {
        if(termFactory == null){
            termFactory = new OntologyTermFactory<>((Class<T>) BaseOntologyTerm.class);
        }
        return termFactory.create(ontologyID);
    }
}
