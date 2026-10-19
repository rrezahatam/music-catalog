package com.example.musiccatalog.named;

import com.example.musiccatalog.config.BasicDataSourceCfg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

interface SingerDao {
    String findNameById(Long id);
}

class NamedTemplateDao implements SingerDao{
    NamedParameterJdbcTemplate namedJdbcTemplate;

    public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return namedJdbcTemplate.queryForObject(
                "SELECT CONCAT(FIRST_NAME, ' ', LAST_NAME) FROM SINGER WHERE ID = :singerId",
                Map.of("singerId", id.toString()),
                String.class);
    }
}

@Import(BasicDataSourceCfg.class)
@Configuration
public class NamedParamCfg{

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


