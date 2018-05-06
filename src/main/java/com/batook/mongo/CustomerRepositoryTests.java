package com.batook.mongo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = MongoConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository repository;

    Customer dave, oliver, carter;

    @Before
    public void setUp() {

        repository.deleteAll();

        dave = repository.save(new Customer("Dave", "Matthews"));
        oliver = repository.save(new Customer("Oliver August", "Matthews"));
        carter = repository.save(new Customer("Carter", "Beauford"));
    }

    @Test
    public void setsIdOnSave() {
        Customer dave = repository.save(new Customer("Dave", "Matthews"));
        assertNotNull(dave.id);
    }

    @Test
    public void findsByLastName() {
        List<Customer> result = repository.findByLastName("Beauford");
    }

    @Test
    public void findsByExample() {
        Customer probe = new Customer(null, "Matthews");
        List<Customer> result = repository.findAll(Example.of(probe));
    }
}