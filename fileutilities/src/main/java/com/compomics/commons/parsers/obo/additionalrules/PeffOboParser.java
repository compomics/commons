package com.compomics.commons.parsers.obo.additionalrules;

import com.compomics.commons.model.Ontology.OntologyTermFactory;
import com.compomics.commons.model.Ontology.specificobotypes.PeffOntologyTerm;
import com.compomics.commons.parsers.obo.OboFlatFileParser;

import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 4/22/2016.
 */
public class PeffOboParser<T extends PeffOntologyTerm> extends OboFlatFileParser<T> {

    private OntologyTermFactory<T> termFactory;

    Pattern possibleClassPattern = Pattern.compile("xsd\\\\:.* ");

    @Override
    protected void additionalParsingRules(T termToExpand) {

        termToExpand.getXrefs().stream()
                .filter(possibleClassPattern.asPredicate())
                .findFirst()
                .ifPresent(e -> termToExpand.setAcceptedClass(getClassForString(e)));

        termToExpand.getRelations().stream()
                .filter(relation -> relation.toLowerCase().contains("has_regexp"))
                .forEach(filteredRelation -> termToExpand.setParsingRule(filteredRelation.split("! ")[1]));

    }

    @Override
        protected T getBaseTerm(String ontologyID) throws ReflectiveOperationException {
            if(termFactory == null){
                termFactory = new OntologyTermFactory<>((Class<T>) PeffOntologyTerm.class);
            }
            return termFactory.create(ontologyID);
    }

    private Class getClassForString(String e) {
        switch (e){
            case "boolean":
                return Boolean.class;
            case "string":
                return String.class;
            case "integer":
                return Integer.class;
        }
        return null;
    }
}
