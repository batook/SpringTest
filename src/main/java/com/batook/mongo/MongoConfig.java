package com.batook.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.batook.mongo")
public class MongoConfig {

    @Bean
    public MongoClient getMongoClient() {
        MongoClient client = null;
        MongoClientURI uri = new MongoClientURI("mongodb://batook:gfhjdjp@cluster0-shard-00-00-739ig.mongodb.net:27017,cluster0-shard-00-01-739ig.mongodb" + ".net:27017,cluster0-shard-00-02-739ig.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");
        client = new MongoClient(uri);
        return client;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(getMongoClient(), "TestDB");
    }

}
