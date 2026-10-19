package com.example.musiccatalog.plain;

import com.example.musiccatalog.config.BasicDataSourceCfg;
import com.example.musiccatalog.config.SimpleDataSourceCfg;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataSourceConfigTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigTest.class);

    @Test
    public void testSimpleDataSource(){
        var ctx = new AnnotationConfigApplicationContext(SimpleDataSourceCfg.class);
        DataSource dataSource = ctx.getBean(SimpleDataSourceCfg.class).dataSource();
        assertNotNull(dataSource);
        testDataSource(dataSource);
        ctx.close();
    }

    @Test
    public void testSpringJdbc(){
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var dataSource = ctx.getBean(BasicDataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        SingerDao dao = ctx.getBean(SingerDao.class);
        assertEquals("John Mayer", dao.findNameById(1L));
        ctx.close();
    }
    public void testDataSource(DataSource dataSource){
        try(var connection = dataSource.getConnection()){
            var statement = connection.prepareStatement("SELECT 1 AS mockValue");
            var resultSet = statement.executeQuery();
            while(resultSet.next()){
                int mockValue = resultSet.getInt("mockValue");
                assertEquals(1, mockValue);
            }
        }catch (SQLException ex){
            LOGGER.debug("Something unexpected happened.", ex);
        }
    }

}
