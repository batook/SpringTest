package com.batook.spring;

import com.batook.mongo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCache {
    public static final Logger log = LoggerFactory.getLogger(TestCache.class);
    static PersonRepository repository;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        log.info(">>> {}", ctx.getDisplayName());
        repository = (PersonRepository) ctx.getBean("personRepository");
        repository.initPersons(Arrays.asList(new Person("Иван", 22), new Person("Сергей", 34), new Person("Игорь", 41)));
        log.info(">>> {}", repository.findCacheByName("Иван"));
        log.info(">>> {}", repository.findCacheByName("Сергей"));
        log.info(">>> {}", repository.findCacheByName("Сергей"));
        log.info(">>> {}", repository.findCacheByName("Иван"));
    }

}

class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    private List<Person> persons = new ArrayList<>();

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
        logger.info("finding person ... {}", name);
        final Person person = findByName(name);
        return person;
    }
}