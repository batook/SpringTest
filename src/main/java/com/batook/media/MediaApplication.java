/**
 *
 */
package com.batook.media;

import com.batook.media.data.JdbcMediaRepository;
import com.batook.media.data.MediaRepository;
import com.batook.media.data.OraConfig;
import com.batook.media.service.ItemListService;
import com.batook.media.service.MediaDOM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Felipe Gutierrez
 */
public class MediaApplication {
    private static final Logger log = LoggerFactory.getLogger(MediaApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(OraConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        MediaRepository repository = context.getBean(JdbcMediaRepository.class);
        ItemListService itemListService = context.getBean(ItemListService.class);

        String dummy = jdbcTemplate.queryForObject("select * from dual", String.class);
        System.out.println(dummy);

        /*
        repository.getList()
                  .forEach(e -> System.out.println(e.entrySet()));
        */
        //new MediaDOM().createXML(itemListService.getItemList(), "test.xml");
    }

}
