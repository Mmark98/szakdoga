package com.example.cultureplacesfinder.model;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.XSD;

public class Heritage {
	 
	private Long id;
	    private String name;
	    private String description;
	    private double latitude;
	    private double longitude;
	    
	    public Heritage() {
}
	    
public Heritage(Long id, String name, String description, double latitude, double longitude) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	    // Getters
	    public Long getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public double getLatitude() {
	        return latitude;
	    }

	    public double getLongitude() {
	        return longitude;
	    }

	    // Setters if needed
	    public void setId(Long id) {
	        this.id = id;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public void setLatitude(double latitude) {
	        this.latitude = latitude;
	    }

	    public void setLongitude(double longitude) {
	        this.longitude = longitude;
	    }

    private static final String NAMESPACE = "http://example.org/ontology/";
    private OntModel model;

    public Heritage(String string, String string2, double d, double e) {
        this.model = ModelFactory.createOntologyModel();
        initializeModel();
    }

    private void initializeModel() {
        model.createOntology(NAMESPACE + "HeritageOntology");

        OntClass heritageClass = model.createClass(NAMESPACE + "Heritage");
        heritageClass.addLabel("Cultural Heritage", "en");

        OntProperty id = model.createOntProperty(NAMESPACE + "id");
        OntProperty name = model.createOntProperty(NAMESPACE + "name");
        OntProperty description = model.createOntProperty(NAMESPACE + "description");
        OntProperty latitude = model.createOntProperty(NAMESPACE + "latitude");
        OntProperty longitude = model.createOntProperty(NAMESPACE + "longitude");

        // Setting ranges for properties using correct fields from XSD
        id.setRange(XSD.xlong);  // Using 'xlong' for long datatype
        name.setRange(XSD.xstring);  // Using 'xstring' for string datatype
        description.setRange(XSD.xstring);  // Also 'xstring' for string datatype
        latitude.setRange(XSD.xdouble);  // 'xdouble' for double datatype
        longitude.setRange(XSD.xdouble);  // 'xdouble' for double datatype
    }

    public void addHeritage(Long id, String name, String description, double lat, double lon) {
        Individual heritage = model.createIndividual(NAMESPACE + "Heritage" + id, model.getResource(NAMESPACE + "Heritage"));
        heritage.addProperty(model.getProperty(NAMESPACE + "id"), model.createTypedLiteral(id));
        heritage.addProperty(model.getProperty(NAMESPACE + "name"), model.createLiteral(name));
        heritage.addProperty(model.getProperty(NAMESPACE + "description"), model.createLiteral(description));
        heritage.addProperty(model.getProperty(NAMESPACE + "latitude"), model.createTypedLiteral(lat));
        heritage.addProperty(model.getProperty(NAMESPACE + "longitude"), model.createTypedLiteral(lon));
    }

    public OntModel getModel() {
        return model;
    }
}
