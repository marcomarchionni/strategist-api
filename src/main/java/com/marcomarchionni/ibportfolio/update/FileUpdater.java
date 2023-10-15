package com.marcomarchionni.ibportfolio.update;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.UpdateService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileUpdater {

    private final UpdateService updateService;

    public FileUpdater(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void update(File flexQueryXml) throws IOException {

        XmlMapper xmlMapper = getXmlMapper();
        FlexQueryResponseDto flexQueryResponseDto = xmlMapper.readValue(flexQueryXml, FlexQueryResponseDto.class);
        updateService.save(flexQueryResponseDto);
    }

    private XmlMapper getXmlMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper;
    }
}
