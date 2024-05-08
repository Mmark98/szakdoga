package com.example.cultureplacesfinder.repository;

import com.example.cultureplacesfinder.model.User;

//import org.apache.jena.query.Dataset;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${fuseki.queryEndpoint}")
    private String queryEndpoint; // SPARQL query endpoint

    @Override
    public Optional<User> findByEmail(String email) {
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        queryStr.setCommandText(
            "SELECT ?name ?username ?password WHERE { " +
            "?user <http://example.org/ontology/email> ?email; " +
            "<http://example.org/ontology/name> ?name; " +
            "<http://example.org/ontology/username> ?username; " +
            "<http://example.org/ontology/password> ?password. " +
            "FILTER (?email = ?emailVal) }"
        );
        queryStr.setLiteral("emailVal", email);

        try (QueryExecution exec = QueryExecutionFactory.create(queryStr.asQuery())) {
            ResultSet results = exec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.next();
                User user = new User();
                user.setName(soln.getLiteral("name").getString());
                user.setUsername(soln.getLiteral("username").getString());
                user.setEmail(email);
                user.setPassword(soln.getLiteral("password").getString());
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

	@Override
	public <S extends User> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

    
}
