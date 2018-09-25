package com.batook.orcl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.batook.orcl")
public class OraDaoConfig {

    @Bean
    public DataSource dataSource() {
        Locale.setDefault(Locale.ENGLISH);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
//        ds.setUrl("jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe");
//        ds.setUsername("batook");
//        ds.setPassword("gfhjdjp");
        ds.setUrl("jdbc:oracle:thin:batook/batook@192.168.1.24:1521/xe");
        return ds;
    }
/*
    @Bean
    public OraRepository oraRepository() {
        return new OraRepository();
    }
*/

}

