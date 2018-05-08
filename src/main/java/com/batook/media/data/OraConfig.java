package com.batook.media.data;

import com.batook.media.service.ItemListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@EnableTransactionManagement
public class OraConfig {

    @Bean
    public DataSource dataSource() {
        Locale.setDefault(Locale.ENGLISH);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe");
        ds.setUsername("batook");
        ds.setPassword("gfhjdjp");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public MediaRepository oraRepository() {
        return new JdbcMediaRepository();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ItemListService itemList() {
        return new ItemListService();
    }

}

