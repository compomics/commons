package com.compomics.commons.model.Ontology;


/**
 * Created by davy on 4/24/2016.
 */
public class OntologyTermFactory<T extends BaseOntologyTerm> {

    private final Class<T> ontologyTermClass;

    public OntologyTermFactory(final Class<T> ontologyTermClass){

        this.ontologyTermClass = ontologyTermClass;
    }

    public T create(String id) throws ReflectiveOperationException {

        return ontologyTermClass.getConstructor(String.class).newInstance(id);
    }
}
