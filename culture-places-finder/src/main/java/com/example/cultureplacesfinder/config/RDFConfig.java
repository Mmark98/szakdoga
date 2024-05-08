/*package com.example.cultureplacesfinder.config;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RDFConfig {
    private static final Logger logger = LoggerFactory.getLogger(RDFConfig.class);

    @Autowired
    private ApplicationContext context;

    @Bean
    public Model jenaModel() {
        Model model = ModelFactory.createDefaultModel();
        try {
            // Betöltjük az első TTL fájlt, a 'user.ttl'-t
            Resource userResource = context.getResource("classpath:rdf/user.ttl");
            if (!userResource.exists()) {
                logger.error("User TTL file not found at classpath:rdf/user.ttl");
                throw new RuntimeException("User TTL file not found.");
            }
            model.read(userResource.getInputStream(), null, "TTL");
            logger.info("Successfully loaded 'user.ttl'");

            // Betöltjük a második TTL fájlt, a 'heritage.ttl'-t
            Resource heritageResource = context.getResource("classpath:rdf/heritage.ttl");
            if (!heritageResource.exists()) {
                logger.error("Heritage TTL file not found at classpath:rdf/heritage.ttl");
                throw new RuntimeException("Heritage TTL file not found.");
            }
            model.read(heritageResource.getInputStream(), null, "TTL");
            logger.info("Successfully loaded 'heritage.ttl'");
            
        } catch (Exception e) {
            logger.error("Error loading TTL files", e);
            throw new RuntimeException("Error loading TTL files", e);
        }
        return model;
    }
}*/