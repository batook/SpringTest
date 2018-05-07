package com.batook.orcl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@EnableTransactionManagement
public class OraDaoConfig {
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
    public OraRepository oraRepository() {
        return new OraRepository();
    }

}

