package com.example.cultureplacesfinder.repository;

import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Repository;

@Repository
public class HeritagesRepositoryImpl implements HeritagesRepository {

	@Override
	public Model findAllHeritages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model findHeritagesByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createHeritage(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHeritage(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Model findHeritagesByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
    // ...
}

