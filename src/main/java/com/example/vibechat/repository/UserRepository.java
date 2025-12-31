package com.example.vibechat.repository;
import com.example.vibechat.model.User;
import com.example.vibechat.service.SparqlService;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Repository
public class UserRepository {

    private final SparqlService sparqlService;

    public UserRepository(SparqlService sparqlService) {
        this.sparqlService = sparqlService;
    }

    public boolean existsByUsername(String username) {
        String askQuery = """
            PREFIX : <http://example.com/schema#>
            ASK WHERE {
                ?u a :User ;
                   :username "%s" .
            }
        """.formatted(username);

        return sparqlService.ask(askQuery);
    }

    public void save(User user) {
        String userIRI = "http://example.com/resource/user/" + user.getUserName();

        String insertQuery = """
            PREFIX : <http://example.com/schema#>
            INSERT DATA {
                <%s> a :User ;
                      :username "%s" ;
                      :password "%s" ;
                      :firstName "%s" ;
                      :lastName "%s" .
            }
        """.formatted(
                userIRI,
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );

        sparqlService.update(insertQuery);
    }

    public Optional<User> findByUsername(String username) {
        String selectQuery = """
            PREFIX : <http://example.com/schema#>
            SELECT ?u ?password ?firstName ?lastName
            WHERE {
                ?u a :User ;
                   :username "%s" ;
                   :password ?password ;
                   :firstName ?firstName ;
                   :lastName ?lastName .
            }
        """.formatted(username);

        List<Map<String, String>> results = sparqlService.select(selectQuery);

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Map<String, String> row = results.getFirst();

        User user = new User();
        user.setUserName(username);
        user.setPassword(row.get("password"));
        user.setFirstName(row.get("firstName"));
        user.setLastName(row.get("lastName"));

        return Optional.of(user);
    }

    public List<String> findAllUsers(){
        String query = """
                    PREFIX : <http://example.com/schema#>
                    
                    SELECT ?username
                    WHERE {
                        ?user a :User ;
                            :username ?username .
                    }
                """;
        return sparqlService.select(query).stream()
                .map(row -> row.get("username"))
                .toList();
    }

}