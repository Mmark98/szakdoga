package com.example.cultureplacesfinder.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;
//import java.util.Collection;

@Service
public class RDFUserDetailsService implements UserDetailsService {
	
	  @Autowired
    private RestTemplate restTemplate;
    
    private final String fusekiQueryEndpoint = "http://localhost:3030/ds/sparql";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String sparqlQuery = """
        		
        		PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX owl: <http://www.w3.org/2002/07/owl#>
            PREFIX ex: <http://example.org/user#>

            SELECT ?email ?password ?role
            WHERE {
                GRAPH <http://example.org/graph/users> {
                    ?user ex:email ?email;
                    ex:role ?role;
                    ex:password ?password.
                    FILTER(?email = "%s"^^xsd:string)
                }
            }
            """.formatted(email);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fusekiQueryEndpoint, sparqlQuery, String.class);
            
            if (response.getBody().contains(email)) {
                String password = extractPassword(response.getBody());
                List<GrantedAuthority> authorities = extractAuthorities(response.getBody());
                return new org.springframework.security.core.userdetails.User(email, password, authorities);
            } else {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (4xx)
            // E.g., HttpStatus.NOT_FOUND (404) if the resource is not found
            // HttpStatus.UNAUTHORIZED (401) if authentication is required
            // HttpStatus.FORBIDDEN (403) if access to the resource is forbidden
            // ...
            throw new UsernameNotFoundException("Error occurred while accessing the RDF store: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            // Handle HTTP server errors (5xx)
            // E.g., HttpStatus.INTERNAL_SERVER_ERROR (500) if there is an internal server error
            // ...
            throw new UsernameNotFoundException("Error occurred on the server side: " + e.getMessage(), e);
        } catch (RestClientException e) {
            // Handle other RestClientExceptions (e.g., when connection fails)
            throw new UsernameNotFoundException("Error occurred during REST request: " + e.getMessage(), e);
        }
    }

    private String extractPassword(String responseBody) throws UsernameNotFoundException {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");
        if (bindings.length() == 0) {
            throw new UsernameNotFoundException("Password data not found in RDF store.");
        }
        JSONObject binding = bindings.getJSONObject(0);
        return binding.getJSONObject("password").getString("value");
    }

    

		private List<GrantedAuthority> extractAuthorities(String responseBody) throws UsernameNotFoundException {
    List<GrantedAuthority> authorities = new ArrayList<>();
    JSONObject jsonObject = new JSONObject(responseBody);
    JSONArray bindings = jsonObject.getJSONObject("results").getJSONArray("bindings");
    if (bindings.length() == 0) {
        throw new UsernameNotFoundException("Authority data not found in RDF store.");
    }
    for (int i = 0; i < bindings.length(); i++) {
        JSONObject binding = bindings.getJSONObject(i);
        String role = binding.getJSONObject("role").getString("value");
        authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
}

}
