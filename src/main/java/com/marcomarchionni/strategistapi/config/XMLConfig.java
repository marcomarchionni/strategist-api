package com.marcomarchionni.strategistapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Configuration
@EnableRetry
public class XMLConfig {

    @Bean(name = "XmlMapper")
    public XmlMapper XmlMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);

        // configure and register JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        xmlMapper.registerModule(javaTimeModule);

        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper;
    }

    @Bean(name = "XmlRestTemplate")
    public RestTemplate xmlRestTemplate(XmlMapper xmlMapper) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        // Add custom xml message converter to the rest template
        MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter =
                new MappingJackson2XmlHttpMessageConverter(xmlMapper);
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(mappingJackson2XmlHttpMessageConverter);

        return restTemplate;
    }
}
