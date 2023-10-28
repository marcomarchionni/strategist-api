package com.marcomarchionni.ibportfolio.services.fetchers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UploadedFileException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FileDataFetcher implements DataFetcher {

    XmlMapper xmlMapper;

    public FileDataFetcher(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public FlexQueryResponseDto fetch(FetchContext context) throws IOException {
        if (context.stream != null) {
            return xmlMapper.readValue(context.stream, FlexQueryResponseDto.class);
        } else {
            throw new UploadedFileException();
        }
    }
}
