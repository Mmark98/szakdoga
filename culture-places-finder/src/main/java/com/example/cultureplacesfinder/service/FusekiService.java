/*package com.example.cultureplacesfinder.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class FusekiService {

    private String fusekiEndpointUrl = "localhost:3030/ds";

    public FusekiService(String fusekiEndpointUrl) {
        this.fusekiEndpointUrl = fusekiEndpointUrl;
    }

    public Model queryData(String sparqlQuery) {
        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiEndpointUrl, query)) {
            return qexec.execConstruct();
        }
    }

    public ResultSet querySelect(String sparqlQuery) {
        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiEndpointUrl, query)) {
            return qexec.execSelect();
        }
    }

    public void updateData(String sparqlUpdate) {
        UpdateRequest request = UpdateFactory.create(sparqlUpdate);
        try (UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, fusekiEndpointUrl)) {
            processor.execute();
        }
    }
}*/
