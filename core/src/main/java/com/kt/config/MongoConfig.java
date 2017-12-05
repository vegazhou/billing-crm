package com.kt.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Administrator on 2015/12/4.
 */

@Configuration
@EnableMongoRepositories(basePackages = {
        "com.kt.repo.mongo",
        "wang.huaichao.data.mongo"
})
@PropertySource("classpath:mongo.properties")
public class MongoConfig extends AbstractMongoConfiguration {

//    @Value("${spring.datasource.webex.url}")
//    private String url;
    //spring.data.mongodb.uri

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;


    @Value("${spring.data.mongodb.database}")
    private String database;


    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }


    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(mongodbUri);
        return new MongoClient(mongoClientURI);
    }
}
