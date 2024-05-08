package com.example.cultureplacesfinder.repository;

import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Repository;

@Repository
public interface HeritagesRepository {
    Model findAllHeritages();
    Model findHeritagesByName(String name);
    void createHeritage(Model model);
    void updateHeritage(Model model);
    Model findHeritagesByUserId(String userId);
}
