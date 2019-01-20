package com.batook.cbr;

import com.batook.cbr.client.DailyInfo;
import com.batook.cbr.client.DailyInfoSoap;
import com.batook.cbr.client.EnumReutersValutesXMLResponse;
import com.batook.cbr.client.GetCursOnDateXMLResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;


/**
 * http://www.cbr.ru/development/DWS/
 * <p>
 * wsimport -s src/main/java/ -p com.batook.cbr.client -Xnocompile DailyInfo.wsdl
 */
public class CbrClient {
    public static final Logger log = LoggerFactory.getLogger(CbrClient.class);

    public static void main(String[] args) {
        try {
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void read() throws Exception {

        // uncomment following code to log http requests to console
/*
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
*/

        DailyInfoSoap service = new DailyInfo().getDailyInfoSoap();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2015, 10, 30); // 2015-11-30 // month starts from 0
        gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        // init date by certain value
        XMLGregorianCalendar date = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        // or request latest date from SOAP service
        date = service.getLatestDateTime();
        System.out.println(date);

        // request currency on the date
        GetCursOnDateXMLResponse.GetCursOnDateXMLResult result = service.getCursOnDateXML(date);
        GetCursOnDateAccessor accessor = GetCursOnDateAccessor.getInstance();


        // get the same currency (USD) by character code and numeric code
        // and print it as a json using Gson as a serializer

        Gson gson = new Gson();
        GetCursOnDateAccessor.Currency currency;

        currency = accessor.getCurrencyByVchCode("USD", result);
        System.out.println(gson.toJson(currency));

        currency = accessor.getCurrencyByVCode("978", result);
        System.out.println(gson.toJson(currency));

        java.util.List<GetCursOnDateAccessor.Currency> currencies = accessor.listCurrencies(result);
        System.out.println(gson.toJson(currencies));


        EnumReutersValutesXMLResponse.EnumReutersValutesXMLResult rv = service.enumReutersValutesXML();
        java.util.List<Object> o = rv.getContent();
        log.info("{}",o);

    }
}
