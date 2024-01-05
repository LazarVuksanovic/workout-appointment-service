package rs.raf.appointmentservice.client.messageservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class MessageServiceClientConfiguration {

    @Bean
    public RestTemplate messageServiceRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8082/api"));
        return restTemplate;
    }
}
