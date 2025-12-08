package com.example.vibechat;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    private String uri = "http://Hemanth:7200";
    private String repoId = "VibeChat";

    @Bean
    public Repository graphDbRepository() {
        Repository repo = new HTTPRepository(uri, repoId);
        repo.init();
        return repo;
    }
}
