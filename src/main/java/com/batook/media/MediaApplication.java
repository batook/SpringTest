/**
 *
 */
package com.batook.media;

import com.batook.media.data.JdbcMediaRepository;
import com.batook.media.data.JdbcRepository;
import com.batook.media.data.MongoConfig;
import com.batook.media.data.OraConfig;
import com.batook.media.service.ItemListService;
import com.batook.media.service.MediaDOM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class MediaApplication {
    private static final Logger log = LoggerFactory.getLogger(MediaApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(OraConfig.class, MongoConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        JdbcRepository repository = context.getBean(JdbcMediaRepository.class);
        ItemListService itemListService = context.getBean(ItemListService.class);

        String dummy = jdbcTemplate.queryForObject("select * from dual", String.class);
        System.out.println(dummy);

        new MediaDOM().createXML(itemListService.getItemList(), "test.xml");
    }
}
