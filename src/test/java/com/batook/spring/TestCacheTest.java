package com.batook.spring;

import com.batook.mongo.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class TestCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(TestCacheTest.class);

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
    }

    private Person findCacheByName(String name) {
        final Person person = personRepository.findCacheByName(name);
        logger.info("find result = {}", person);
        return person;
    }
}