package com.blog.demo.Elastic.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.blog.demo.Elastic.repository")
//@ComponentScan(basePackages = {"com.blog.demo.Elastic.service"})
public class ElasticConfig extends ElasticsearchConfiguration {
    @Value("${es.hostandport}")
    String hostandport;
    @Value("${es.user}")
    String user;
    @Value("${es.password}")
    String password;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(hostandport)
                .withBasicAuth(user, password)
                .build();
    }
}

