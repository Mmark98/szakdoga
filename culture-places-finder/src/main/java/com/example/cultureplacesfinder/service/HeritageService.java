package com.example.cultureplacesfinder.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.cultureplacesfinder.model.Heritage;
@Service
public class HeritageService {

    private final String fusekiUpdateEndpoint = "http://localhost:3030/ds/update";
    private final String jenaQueryEndpoint="http://localhost:3030/ds/update";

    @Autowired
    private RestTemplate restTemplate;
    
    // private JenaClient jenaClient;
    
    public void insertHeritageData(String id, String name, String description, double latitude, double longitude) {
        String sparqlUpdate = String.format("""
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX ex: <http://example.org/schema#>
            INSERT DATA {
              GRAPH <http://example.org/graph/heritages> {
                ex:heritage%s
                    rdf:type        ex:Heritage;
                    ex:name         "%s";
                    ex:description  "%s";
                    ex:id           "%s"^^xsd:long;
                    ex:latitude     "%f"^^xsd:double;
                    ex:longitude    "%f"^^xsd:double.
              }
            }
            """, id, name, description, id, latitude, longitude);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/sparql-update"));
        HttpEntity<String> request = new HttpEntity<>(sparqlUpdate, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(fusekiUpdateEndpoint, request, String.class);

        System.out.println("Response from Fuseki: " + response.getStatusCode());
    }
    
    
  /*  public List<Heritage> findHeritagesByNamek(String name) {
        String sparqlQuery = String.format("""
            PREFIX ex: <http://example.org/schema#>

            SELECT ?heritage ?name ?description ?latitude ?longitude
            WHERE {
              GRAPH <http://example.org/graph/heritages> {
                ?heritage rdf:type ex:Heritage ;
                          ex:name ?name ;
                          ex:description ?description ;
                          ex:latitude ?latitude ;
                          ex:longitude ?longitude .
                FILTER regex(?name, "%s", "i")
              }
            }
            """, name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/sparql-query"));
        HttpEntity<String> request = new HttpEntity<>(sparqlQuery, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(jenaQueryEndpoint, request, String.class);

        // Feldolgozás: itt kellene kinyerned az adatokat a válaszból és Heritage objektumokká alakítanod
        // Ezt a részt igény szerint kell megvalósítani.

        return parseHeritageFromJsonResponse(response);
    }
    public List<Heritage> parseHeritageFromJsonResponse(ResponseEntity<String> response) {
        List<Heritage> heritages = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray results = jsonResponse.getJSONObject("results").getJSONArray("bindings");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            Long id = Long.valueOf(result.getJSONObject("heritage").getString("value").hashCode());// Példa, ha a URI az azonosító
            String name = result.getJSONObject("name").getString("value");
            String description = result.getJSONObject("description").getString("value");
            Double latitude = Double.parseDouble(result.getJSONObject("latitude").getString("value"));
            Double longitude = Double.parseDouble(result.getJSONObject("longitude").getString("value"));

            heritages.add(new Heritage(id, name, description, latitude, longitude));
        }
        return heritages;
    }*/
   // ------------------------------------------------------------
  

    public List<Heritage> findHeritagesByName(String name) {
    	String sparqlQuery = String.format("""
                PREFIX ex: <http://example.org/schema#>

                SELECT ?heritage ?name ?description ?latitude ?longitude
                WHERE {
                  GRAPH <http://example.org/graph/heritages> {
                    ?heritage rdf:type ex:Heritage ;
                              ex:name ?name ;
                              ex:description ?description ;
                              ex:latitude ?latitude ;
                              ex:longitude ?longitude .
                    FILTER regex(?name, "%s", "i")
                  }
                }
                """, name); // SPARQL lekérdezés, mint korábban definiálva
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/sparql-update"));
        HttpEntity<String> request = new HttpEntity<>(sparqlQuery, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(jenaQueryEndpoint, request, String.class);
        
        return parseHeritageFromJsonResponse(response);
    }

    private List<Heritage> parseHeritageFromJsonResponse(ResponseEntity<String> response) {
        List<Heritage> heritages = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray results = jsonResponse.getJSONObject("results").getJSONArray("bindings");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            Long id = Long.valueOf(Integer.toString(result.getJSONObject("heritage").getString("value").hashCode())); // Az URI hash-kódja mint ID
            String name = result.getJSONObject("name").getString("value");
            String description = result.getJSONObject("description").getString("value");
            Double latitude = Double.parseDouble(result.getJSONObject("latitude").getString("value"));
            Double longitude = Double.parseDouble(result.getJSONObject("longitude").getString("value"));

            heritages.add(new Heritage(id, name, description, latitude, longitude));
        }
        return heritages;
    }
}
