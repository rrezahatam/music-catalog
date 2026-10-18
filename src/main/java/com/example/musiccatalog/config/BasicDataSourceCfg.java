package com.example.musiccatalog.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class BasicDataSourceCfg {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataSourceCfg.class);

    @Value("${jdbc.driverClassName}")
    String driverClassName;
    @Value("${jdbc.url}")
    String url;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        try{
            var dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception ex){
            LOGGER.error("DBCP DataSource bean cannot be created!", ex);
            return null;
        }
    }

}
