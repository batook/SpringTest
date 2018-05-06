package com.batook.mongo;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@ContextConfiguration(classes = MongoConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {

    MongoOperations mongoOps;

    @Autowired
    MongoClient mongo;

    @Before
    public void drop() {
        mongoOps = new MongoTemplate(mongo, "database");
        mongoOps.dropCollection("person");
    }

    @Test
    public void test() {
        assertNotNull(mongo);
        assertNotNull(mongoOps);
        MongoCursor<String> i = mongo.listDatabaseNames().iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
        mongoOps.insert(new Person("Joe", 34));
        assertEquals("Joe", mongoOps.findOne(new Query(where("name").is("Joe")), Person.class).getName());
        assertEquals(1, mongoOps.getCollection("person").count());
        Query query = new Query(where("name").is("Sergey").and("age").is(45));
        Update update = new Update().set("age", 18);
        WriteResult wr = mongoOps.upsert(query, update, Person.class);
        System.out.println(wr);
        Person p = mongoOps.findOne(new Query(where("name").is("Sergey")), Person.class);
        assertThat(p.getAge(), is(18));
        System.out.println(mongoOps.getCollection("person").count());
        mongoOps.remove(new Query(where("name").is("Sergey")), Person.class);
        assertEquals(1, mongoOps.getCollection("person").count());
    }
}

