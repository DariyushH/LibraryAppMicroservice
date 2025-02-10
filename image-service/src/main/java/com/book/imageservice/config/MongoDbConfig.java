package com.book.imageservice.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        return new GridFsTemplate(mongoDbFactory, mongoConverter);
    }
    @Override
    protected String getDatabaseName() {
        return "mongo";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://mongo:27017");
    }
}
