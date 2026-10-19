package com.example.musiccatalog.plain;


import com.example.musiccatalog.config.BasicDataSourceCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;

interface SingerDao {
    String findNameById(Long id);
}

class JdbcSingerDao implements SingerDao, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSingerDao.class);
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null)
            throw new BeanCreationException("Must set dataSource on SpringDao");
    }

    @Override
    public String findNameById(Long id) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement(
                    "SELECT FIRST_NAME, LAST_NAME FROM SINGER WHERE ID=" + id);
            var resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getString("FIRST_NAME") + " "
                        + resultSet.getString("LAST_NAME");
        } catch (SQLException ex) {
            LOGGER.error("Problem when executing SELECT!", ex);
        }
        return null;
    }

}


@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringDatasourceCfg {
    @Autowired
    DataSource dataSource;

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setDataSource(dataSource);
        return dao;
    }
}
