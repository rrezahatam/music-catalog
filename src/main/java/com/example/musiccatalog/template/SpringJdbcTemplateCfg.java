package com.example.musiccatalog.template;

import com.example.musiccatalog.config.BasicDataSourceCfg;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.musiccatalog.QueryConstants.PARAMETRIZED_FIND_NAME;

interface SingerDao{
    String findNameById(Long id);
}

class JdbcSingerDao implements SingerDao, InitializingBean {
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return jdbcTemplate.queryForObject(PARAMETRIZED_FIND_NAME, String.class, id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null)
            throw new BeanCreationException("Must set idbcTemplate on SpringDao");
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringJdbcTemplateCfg {
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        var jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public SingerDao singerDao(){
        var dao = new JdbcSingerDao();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }
}