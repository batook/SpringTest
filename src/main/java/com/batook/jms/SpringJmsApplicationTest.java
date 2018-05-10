package com.batook.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(classes = {SenderConfig.class, ReceiverConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJmsApplicationTest {

    @Autowired
    private Sender sender;
    @Autowired
    private Receiver receiver;

    @Test
    public void testReceive() throws Exception {
        sender.send("MyQueue", "Hello Spring JMS ActiveMQ!");

        receiver.getLatch()
                .await(10000, TimeUnit.MILLISECONDS);
        System.out.println(receiver.getLatch()
                                   .getCount());

    }
}