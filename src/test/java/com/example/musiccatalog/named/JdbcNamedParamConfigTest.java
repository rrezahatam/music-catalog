package com.example.musiccatalog.named;

import com.example.musiccatalog.config.EmbeddedJdbcConfig;
import com.example.musiccatalog.plain.pojos.Singer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcNamedParamConfigTest {

    @Test
    public void testSpringJdbc(){
        var ctx = new AnnotationConfigApplicationContext(TestDbCfg.class);
        var dataSource = ctx.getBean(DataSource.class);
        var namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
        assertNotNull(dataSource);
        assertNotNull(namedParameterJdbcTemplate);

        var dao = ctx.getBean(SingerDao.class);
        assertEquals("John Mayer", dao.findNameById(1L));
    }


    @Import(EmbeddedJdbcConfig.class)
    @Configuration
    public static class TestDbCfg{

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public SingerDao singerDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            NamedTemplateDao dao = new NamedTemplateDao();
            dao.setNamedJdbcTemplate(namedParameterJdbcTemplate);
            return dao;
        }

    }
}
