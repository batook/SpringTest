package com.batook.aq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OraRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getDummy() {
        return jdbcTemplate.queryForObject("select * from dual", String.class);
    }

    public String getDummyRS() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from dual");
        String result = "";
        while (rs.next()) {
            result = rs.getString(1);
        }
        return result;
    }
}
