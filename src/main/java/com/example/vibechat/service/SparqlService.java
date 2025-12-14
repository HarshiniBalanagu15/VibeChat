package com.example.vibechat.service;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.query.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlService {

    private final Repository repo;

    public SparqlService(Repository repo) {
        this.repo = repo;
    }

    /* SELECT queries */
    public List<Map<String, String>> select(String sparql) {
        try (RepositoryConnection conn = repo.getConnection()) {
            TupleQuery query = conn.prepareTupleQuery(sparql);
            try (TupleQueryResult result = query.evaluate()) {
                List<Map<String, String>> list = new ArrayList<>();
                while (result.hasNext()) {
                    BindingSet bs = result.next();
                    Map<String, String> row = new HashMap<>();
                    for (String name : bs.getBindingNames()) {
                        row.put(name, bs.getValue(name).stringValue());
                    }
                    list.add(row);
                }
                return list;
            }
        }
    }

    /** ASK queries */
    public boolean ask(String sparql) {
        try (RepositoryConnection conn = repo.getConnection()) {
            BooleanQuery query = conn.prepareBooleanQuery(sparql);
            return query.evaluate();
        }
    }

    /** INSERT / DELETE / UPDATE queries */
    public void update(String sparql) {
        try (RepositoryConnection conn = repo.getConnection()) {
            Update update = conn.prepareUpdate(sparql);
            update.execute();
        }
    }
}
