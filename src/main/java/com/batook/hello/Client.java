package com.batook.hello;

import com.batook.cbr.client.GetCursOnDateXML;
import com.batook.cbr.client.GetCursOnDateXMLResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class Client extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public GetCursOnDateXMLResponse getResponce() {

        GetCursOnDateXML request = new GetCursOnDateXML();
        request.getOnDate();

        log.info("request {}",request);
        GetCursOnDateXMLResponse response = (GetCursOnDateXMLResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }

}
