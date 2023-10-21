package com.marcomarchionni.ibportfolio.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Configuration
@Slf4j
public class XMLConvertersConfig {

    @Bean
    public MappingJackson2HttpMessageConverter jsonMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean(name = "XmlMapper")
    public XmlMapper XmlMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper;
    }

    @Bean(name = "XmlMessageConverter")
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(XmlMapper xmlMapper) {
        return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
    }

    @Bean(name = "XmlRestTemplate")
    public RestTemplate restTemplate(MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            log.debug("Request Headers: {}", request.getHeaders());
            return execution.execute(request, body);
        }));

        restTemplate.getMessageConverters().add(0, mappingJackson2XmlHttpMessageConverter);
        log.info("Message Converters: " + restTemplate.getMessageConverters());
        return restTemplate;
    }
}
