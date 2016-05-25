package com.compomics.commons.model.Ontology.specificobotypes;

import com.compomics.commons.model.Ontology.BaseOntologyTerm;

import java.util.regex.Pattern;

/**
 * Created by davy on 4/22/2016.
 */
public class PeffOntologyTerm extends BaseOntologyTerm {


    private Class acceptedClass;

    private Pattern parsingRule;

    public PeffOntologyTerm(String ontology) {
        super(ontology);
    }

    public Class getAcceptedClass() {
        return acceptedClass;
    }

    public void setAcceptedClass(Class acceptedClass) {
        this.acceptedClass = acceptedClass;
    }

    public Pattern getParsingRule(){
        return parsingRule;
    }

    public BaseOntologyTerm setParsingRule(String rule){
        parsingRule = Pattern.compile(rule);
        return this;
    }

}
