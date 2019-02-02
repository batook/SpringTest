package com.batook.spring;

import com.batook.mongo.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class TestCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(TestCacheTest.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PersonRepository personRepository;

    @Before
    public void before() {
        personRepository.initPersons(Arrays.asList(new Person("Иван", 22), new Person("Сергей", 34), new Person("Игорь", 41)));
    }

    @Test
    public void testCache() {
        findCacheByName("Иван");
        findCacheByName("Сергей");
        findCacheByName("Сергей");
        findCacheByName("Иван");
        personRepository.findCacheByName("Иван");
        personRepository.checkCache();
        personRepository.clearCache("person");
        checkCache();
        logger.info("{}", personRepository.findCacheByName("Иван"));
        logger.info("{}", personRepository.findCacheByName("Иван"));
    }

    private Person findCacheByName(String name) {
        final Person person = personRepository.findCacheByName(name);
        logger.info("find result = {}", person);
        return person;
    }

    private void checkCache() {
        Collection<String> cacheList = cacheManager.getCacheNames();
        for (String s : cacheList) {
            Cache c = cacheManager.getCache(s);
            //Return the the underlying native cache provider (ConcurrentMapCache)
            ConcurrentMap<Object, Object> store = (ConcurrentMap<Object, Object>) c.getNativeCache();
            logger.info("cache {} = {}", s, store.entrySet());
        }
    }
}