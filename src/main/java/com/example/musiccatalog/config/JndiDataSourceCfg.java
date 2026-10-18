package com.example.musiccatalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiTemplate;

import javax.sql.DataSource;

public class JndiDataSourceCfg {
    private static final Logger LOGGER = LoggerFactory.getLogger(JndiDataSourceCfg.class);

    @Bean
    public DataSource dataSource(){
        try{
            return (DataSource) new JndiTemplate().lookup("java:comp/env/jdbc/music-db");
        } catch (Exception ex){
            LOGGER.error("JndiDataSource been can not be created!", ex);

            return null;
        }
    }
}
