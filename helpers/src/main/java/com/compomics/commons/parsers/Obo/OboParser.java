package com.compomics.commons.parsers.Obo;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.model.Ontology.OntologyTerm;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 4/19/2016.
 */
public class OboParser {

    private static final Pattern namePattern = Pattern.compile("name: .*\\n");
    private static final Pattern idPattern = Pattern.compile("id: [A-Z]*:[0-9]*\\n");
    //could technically remove xsd from pattern
    private static final Pattern xrefPattern = Pattern.compile("xref: xsd\\\\:.* ");

    private static final Pattern relationPattern = Pattern.compile("(is-a|has-a): [A-Z]*:[0-9]* ");
    private static final Pattern definitionPattern = Pattern.compile("\".*\"");

    private static final Pattern allowedClassesPattern = Pattern.compile("(string|integer|boolean)");

    public static Map<String, OntologyTerm> parseOboFile(Path oboFile) throws IOException {

        StringBuilder termBlock = new StringBuilder();

        LineReader reader = new LineReader(new FileReader(oboFile.toFile()));
        String line = reader.readLine();

        Map<String, OntologyTerm> possibleHeaders = new HashMap<>();

        while (line != null) {
            //only need terms
            if (line.toLowerCase().contains("[term]")) {
                if (termBlock.length() != 0) {
                    try {
                        //todo add check for needed vs unneeded terms instead of throwing exception
                        OntologyTerm createdTerm = createBlock(termBlock.toString());
                        possibleHeaders.put(createdTerm.getName(), createdTerm);
                    } catch (MissingFormatArgumentException e) {
                        //atm just print out, talk about what to do with unneeded terms, probably ignore
                        e.printStackTrace();
                    }
                    termBlock = new StringBuilder();
                }
            }
            termBlock.append(line);
            line = reader.readLine();
        }
        return possibleHeaders;
    }

    private static OntologyTerm createBlock(String termblock) throws MissingFormatArgumentException {

        Matcher nameMatcher = namePattern.matcher(termblock);
        Matcher ontologyTermMatcher = idPattern.matcher(termblock);
        Matcher allowedClassMatcher = xrefPattern.matcher(termblock);

        boolean foundName = nameMatcher.find();
        boolean foundOntology = ontologyTermMatcher.find();
        boolean foundClass = allowedClassMatcher.find();
        if (!foundName || !foundOntology || !foundClass) {
            throw new MissingFormatArgumentException("missing needed information: name found: " + foundName + ", ontology found: " + foundOntology + ", class found: " + foundClass);
        }

        OntologyTerm toReturn = new OntologyTerm(nameMatcher.toMatchResult().group(), ontologyTermMatcher.toMatchResult().group(), ClassFromString(allowedClassMatcher.toMatchResult().group()));

        Matcher definitionMatcher = definitionPattern.matcher(termblock);
        boolean hasDefinition = definitionMatcher.find();

        if (hasDefinition) {
            toReturn.addDefinition(definitionMatcher.toMatchResult().group());
        }

        Matcher relationMatcher = relationPattern.matcher(termblock);
        boolean hasRelation = relationMatcher.find();

        if (hasRelation) {
            toReturn.addRelation(relationMatcher.group());
            //add regex
            if (toReturn.getRelation().toLowerCase().contains("has_regexp")) {
                toReturn.setParsingRule(toReturn.getRelation().split("! ")[1]);
            }
        }


        return toReturn;
    }

    private static Class ClassFromString(String group) {
        Class toReturn = null;
        Matcher classFoundMatcher = allowedClassesPattern.matcher(group);

        if (classFoundMatcher.find()) {

            switch (classFoundMatcher.group()) {
                case "string":
                    toReturn = String.class;
                    break;
                case "boolean":
                    toReturn = Boolean.class;
                    break;
                case "integer":
                    toReturn = Integer.class;
                    break;
            }
        }
        return toReturn;
    }
}
