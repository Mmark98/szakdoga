package com.example.cultureplacesfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.query.ResultSet;
import com.example.cultureplacesfinder.model.User;

@Service
public class UserService {

	
   // private JenaClient jenaClient;
    private final String fusekiUpdateEndpoint = "http://localhost:3030/ds/update";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String fusekiQueryEndpoint="http://localhost:3030/ds/sparql";
    
   

    public void registerUser(User user) {
        String sparqlUpdate = String.format("""
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX ex: <http://example.org/user#>
            INSERT DATA {
              GRAPH <http://example.org/graph/users> {
                <http://example.org/user#%s>
                        rdf:type            ex:User;
                        ex:email            "%s";
                        ex:id               "%s"^^xsd:long;
                        ex:name             "%s";
                        ex:password         "%s";
                        ex:role             "USER";
                        ex:username         "%s".
              }
            }
            """, user.getId(), user.getEmail(), user.getId(), user.getName(), user.getPassword(), user.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/sparql-update"));
        HttpEntity<String> request = new HttpEntity<>(sparqlUpdate, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(fusekiUpdateEndpoint, request, String.class);
    }

    
    public boolean authenticateUser(String email, String password) {
        ParameterizedSparqlString psss = new ParameterizedSparqlString();
        psss.setCommandText("""
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX owl: <http://www.w3.org/2002/07/owl#>
            PREFIX ex: <http://example.org/user#>

            SELECT ?email ?password
            WHERE {
                GRAPH <http://example.org/graph/users> {
                    ?user ex:email ?email;
                          ex:password ?password.
                    FILTER(?email = ?emailParam)
                }
            }
            """);
        psss.setLiteral("emailParam", email);

        try (RDFConnection conn = RDFConnectionFactory.connect(fusekiQueryEndpoint)) {
            Query query = psss.asQuery();
            System.out.println(query);
            try (QueryExecution qExec = conn.query(query)) {
                ResultSet results = qExec.execSelect();
                if (results.hasNext()) {
                    String storedPassword = results.next().getLiteral("password").getString();
                    return passwordEncoder.matches(password, storedPassword);
                }
            }
        }
        return false;
    }


    
   
    
    
    public class UserDetails {
        private String email;
        private String password;

        public UserDetails(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
     }
    
    
}
