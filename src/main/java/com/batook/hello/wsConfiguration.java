package com.batook.hello;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class wsConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.batook.cbr.client");
        return marshaller;
    }

    @Bean
    public Client wsClient(Jaxb2Marshaller marshaller) {
        Client client = new Client();
        client.setDefaultUri("http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
