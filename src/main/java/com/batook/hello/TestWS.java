package com.batook.hello;

import com.batook.cbr.client.GetCursOnDateXMLResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = wsConfiguration.class)
public class TestWS {

    @Autowired
    private Client wsClient;


    @Test
    public void play() {
        GetCursOnDateXMLResponse response = wsClient.getResponce();
        System.err.println(response.getGetCursOnDateXMLResult());
    }

}