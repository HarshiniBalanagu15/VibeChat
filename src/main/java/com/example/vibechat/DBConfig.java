package com.example.vibechat;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DBConfig {
    @Value("${GRAPHDB_URI}")
    private String uri ;
    @Value("${GRAPHDB_REPO_ID}")
    private String repoId;

    @Bean
    public Repository graphDbRepository() {
        Repository repo = new HTTPRepository(uri, repoId);
        repo.init();
        return repo;
    }

    //Password encryption bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
