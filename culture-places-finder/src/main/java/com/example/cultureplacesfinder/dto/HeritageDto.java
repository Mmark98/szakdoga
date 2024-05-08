package com.example.cultureplacesfinder.dto;

import java.util.UUID;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

public class HeritageDto {
    private UUID id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public Resource toRDFResource(Model model) {
        Resource heritageResource = model.createResource("http://example.org/heritage/" + id)
            .addProperty(model.createProperty("http://example.org/ontology/name"), name)
            .addProperty(model.createProperty("http://example.org/ontology/description"), description)
            .addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat"), String.valueOf(latitude))
            .addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long"), String.valueOf(longitude));

        return heritageResource;
    }
}
