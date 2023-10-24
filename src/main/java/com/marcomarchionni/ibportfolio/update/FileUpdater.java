package com.marcomarchionni.ibportfolio.update;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.DataSaverService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUpdater {

    private final DataSaverService dataSaverService;

    private final XmlMapper xmlMapper;

    public FileUpdater(DataSaverService dataSaverService, XmlMapper xmlMapper) {
        this.dataSaverService = dataSaverService;
        this.xmlMapper = xmlMapper;
    }

    public void update(File flexQueryXml) throws IOException {

        FlexQueryResponseDto flexQueryResponseDto = xmlMapper.readValue(flexQueryXml, FlexQueryResponseDto.class);
        dataSaverService.save(flexQueryResponseDto);
    }

    public void update(InputStream flexQueryXml) throws IOException {

        FlexQueryResponseDto flexQueryResponseDto = xmlMapper.readValue(flexQueryXml, FlexQueryResponseDto.class);
        dataSaverService.save(flexQueryResponseDto);
    }
}
