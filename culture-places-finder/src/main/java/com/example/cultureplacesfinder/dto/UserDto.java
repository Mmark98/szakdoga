package com.example.cultureplacesfinder.dto;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
//import org.apache.jena.rdf.model.Literal;
import org.apache.jena.vocabulary.RDF;

import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String roles; // This should ideally be a collection if multiple roles are allowed

    private static final String NAMESPACE = "http://example.org/user/";

    public UserDto() {}

    public UserDto(UUID id, String name, String username, String email, String password, String roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }

    public Resource toRDFResource(Model model) {
        // Create the user resource with a unique URI based on user ID
        Resource userResource = model.createResource(NAMESPACE + "User/" + id)
            .addProperty(RDF.type, model.createResource(NAMESPACE + "User"))
            .addProperty(model.createProperty(NAMESPACE, "name"), name)
            .addProperty(model.createProperty(NAMESPACE, "username"), username)
            .addProperty(model.createProperty(NAMESPACE, "email"), email)
            .addProperty(model.createProperty(NAMESPACE, "password"), password)
            .addProperty(model.createProperty(NAMESPACE, "role"), roles);

        return userResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
               Objects.equals(name, userDto.name) &&
               Objects.equals(username, userDto.username) &&
               Objects.equals(email, userDto.email) &&
               Objects.equals(password, userDto.password) &&
               Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, password, roles);
    }

    @Override
    public String toString() {
        return "UserDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", roles='" + roles + '\'' +
               '}';
    }
}
