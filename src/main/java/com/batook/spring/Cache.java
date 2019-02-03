package com.batook.spring;

import com.batook.mongo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;

public class Cache {
    public static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);
    static PersonRepository repository;

    public static void main(String[] args) throws IOException {
        System.setProperty("spring.profiles.active", "dev");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        //        ((ConfigurableEnvironment)ctx.getEnvironment()).setActiveProfiles("dev");
        //        ((ClassPathXmlApplicationContext) ctx).refresh();
        System.getProperties()
              .storeToXML(new FileOutputStream("Properties.xml"), "Properties");
        LOGGER.info("CLASSPATH: {}", ctx.getEnvironment().getProperty("java.class.path"));
        LOGGER.info(">>> {}", ctx.getEnvironment());
        repository = (PersonRepository) ctx.getBean("personRepository");
        repository.initPersons(Arrays.asList(new Person("Иван", 22), new Person("Сергей", 34), new Person("Игорь", 41)));
        LOGGER.info(">>> {}", repository.findCacheByName("Иван"));
        LOGGER.info(">>> {}", repository.findCacheByName("Сергей"));
        LOGGER.info(">>> {}", repository.findCacheByName("Сергей"));
        LOGGER.info(">>> {}", repository.findCacheByName("Иван"));
    }

}

class PersonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepository.class);

    private CacheManager cacheManager;
    private List<Person> persons = new ArrayList<>();

    @Autowired
    public void setCacheManager(CacheManager cm) {
        this.cacheManager = cm;
    }

    void initPersons(List<Person> persons) {
        this.persons.addAll(persons);
    }

    private Person findByName(String name) {
        return persons.stream()
                      .filter(p -> p.getName()
                                    .equals(name))
                      .findFirst()
                      .orElse(null);
    }

    public boolean delete(String name) {
        final Person person = findByName(name);
        return persons.remove(person);
    }

    public boolean add(Person person) {
        return persons.add(person);
    }

    @Cacheable(cacheNames = "person")
    public Person findCacheByName(String name) {
        LOGGER.info("finding person ... {}", name);
        return findByName(name);
    }

    public void checkCache() {
        Collection<String> cacheList = cacheManager.getCacheNames();
        for (String s : cacheList) {
            org.springframework.cache.Cache c = cacheManager.getCache(s);
            //Return the the underlying native cache provider (ConcurrentMapCache)
            ConcurrentMap<Object, Object> store = (ConcurrentMap<Object, Object>) c.getNativeCache();
            LOGGER.info("cache {} = {}", s, store.entrySet());
        }
    }

    public void clearCache(String name) {
        org.springframework.cache.Cache c = cacheManager.getCache(name);
        LOGGER.info("Clearing cache {} size={}", name, ((Map) c.getNativeCache()).size());
        c.clear();
    }
}