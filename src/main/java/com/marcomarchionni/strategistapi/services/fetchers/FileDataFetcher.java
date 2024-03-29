package com.marcomarchionni.strategistapi.services.fetchers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EmptyFileException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidXMLFileException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.NoXMLExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class FileDataFetcher implements DataFetcher {

    private final XmlMapper xmlMapper;

    @Override
    public FlexQueryResponseDto fetch(UpdateContext context) {
        validateFile(context.getFile());

        try (InputStream stream = context.getFile().getInputStream()) {
            return xmlMapper.readValue(stream, FlexQueryResponseDto.class);
        } catch (Exception ex) {
            throw new InvalidXMLFileException(ex);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException();
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".xml")) {
            throw new NoXMLExtensionException();
        }
    }
}
