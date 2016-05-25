package com.compomics.commons.model.Ontology;

import java.util.ArrayList;

/**
 * Created by Davy Maddelein on 4/19/2016.
 */
public class BaseOntologyTerm {

    //mandatory by spec
    private final String ontology;

    public BaseOntologyTerm(String ontology) {
        this.ontology = ontology;
    }

    public String getOntology() {
        return ontology;
    }


    //optional

    private ArrayList<String> relations = new ArrayList<>();

    public BaseOntologyTerm addRelation (String relation){
        relations.add(relation);
        return this;
    }

    public ArrayList<String> getRelations() {
        return relations;
    }


    private String definition = "";

    public BaseOntologyTerm addDefinition(String definition){
        this.definition = definition;
        return this;
    }

    public String getDefinition() {
        return definition;
    }


    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private ArrayList<String> xrefs = new ArrayList<>();

    public void addXref(String xref) {
        xrefs.add(xref);
    }

    public ArrayList<String> getXrefs(){
        return xrefs;
    }

}
