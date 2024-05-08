package com.example.cultureplacesfinder.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import java.util.UUID;

public class User {
    private UUID id;  // A felhasználó egyedi azonosítója
    private String name;
    private String username;
    private String email;
    private String password;
    private String role = "USER";

    private static final String NAMESPACE = "http://example.org/user/";

    public User() {
    }

    public User(UUID id, String name, String username, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getterek és setterek
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Resource addToModel(Model model) {
        // Az URI most az ID-t is tartalmazza, biztosítva az egyediséget
        Resource userResource = model.createResource(NAMESPACE + id.toString())
            .addProperty(RDF.type, model.createResource("http://example.org/ontology/User"))
            .addProperty(model.createProperty("http://example.org/ontology/name"), name)
            .addProperty(model.createProperty("http://example.org/ontology/username"), username)
            .addProperty(model.createProperty("http://example.org/ontology/email"), email)
            .addProperty(model.createProperty("http://example.org/ontology/password"), password)
            .addProperty(model.createProperty("http://example.org/ontology/role"), role);

        return userResource;
    }
}
