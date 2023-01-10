package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.services.UpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileUpdaterTest {

    @Mock
    UpdateServiceImpl updateService;

    FileUpdater fileUpdater;

    @Captor
    ArgumentCaptor<FlexQueryResponse> captor;

    @BeforeEach
    void setup() {
        fileUpdater = new FileUpdater(updateService);
    }

    @Test
    void xmlToDtoSuccess() throws IOException {
        File flexQueryXml = loadFile("flex/SimpleJune2022.xml");
        LocalDate expectedFromDate = LocalDate.of(2022, 6, 1);
        int expectedTradesSize = 10;
        int expectedPositionsSize = 7;
        int expectedClosedDividendsSize = 14;
        int expectedOpenDividendsSize = 3;

        fileUpdater.update(flexQueryXml);

        verify(updateService).save(captor.capture());
        FlexQueryResponse response = captor.getValue();

        assertNotNull(response);
        LocalDate actualFromDate = response.getFlexStatement().getFromDate();
        assertNotNull(actualFromDate);
        assertEquals(expectedFromDate, actualFromDate);
        assertEquals(expectedTradesSize, response.getTradesDto().size());
        assertEquals(expectedPositionsSize, response.getPositionsDto().size());
        assertEquals(expectedClosedDividendsSize, response.getClosedDividendsDto().size());
        assertEquals(expectedOpenDividendsSize, response.getOpenDividendsDto().size());
    }

    @Test
    @Disabled
        //TODO: xml validation
    void invalidXml() {
        File invalidFlexQueryXml = loadFile("flex/InvalidJune2022.xml");

        assertThrows(Exception.class, () -> fileUpdater.update(invalidFlexQueryXml));
    }

    private File loadFile(String filePath) {
        return new File(Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResource(filePath)).getFile());
    }
}