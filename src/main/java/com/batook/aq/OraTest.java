package com.batook.aq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OraDaoConfig.class)
public class OraTest {
    @Autowired
    OraRepository repository;

    @Test
    public void dummy() {
        assertNotNull(repository);
        assertEquals("X", repository.getDummy());
    }

}
