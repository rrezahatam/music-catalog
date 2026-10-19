package com.example.musiccatalog.template;

import com.example.musiccatalog.config.EmbeddedJdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcTemplateConfigTest {

    @Test
    void testSpringJdbc(){
        var ctx = new AnnotationConfigApplicationContext(TestDbCfg.class);
        DataSource dataSource = ctx.getBean(DataSource.class);
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        SingerDao dao = ctx.getBean(SingerDao.class);

        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(dao);

        assertEquals("John Mayer", dao.findNameById(1L));
    }

    @Import(EmbeddedJdbcConfig.class)
    @Configuration
    public static class TestDbCfg{

        @Bean
        public SingerDao singerDao(JdbcTemplate jdbcTemplate){
            JdbcSingerDao dao = new JdbcSingerDao();
            dao.setJdbcTemplate(jdbcTemplate);
            return dao;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            return jdbcTemplate;
        }
    }
}
