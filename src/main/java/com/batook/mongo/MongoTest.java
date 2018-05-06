package com.batook.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@ContextConfiguration(classes = MongoConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {

    @Autowired
    MongoOperations mongoOps;

    @Autowired
    MongoClient mongo;

    @Test
    public void test() {
        assertNotNull(mongo);
        MongoCursor<String> i = mongo.listDatabaseNames().iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
        mongoOps = new MongoTemplate(mongo, "database");
        mongoOps.insert(new Person("Joe", 34));
        assertEquals("Joe", mongoOps.findOne(new Query(where("name").is("Joe")), Person.class).getName());
        assertEquals(1,mongoOps.getCollection("person").count());
        mongoOps.dropCollection("person");
    }
}

