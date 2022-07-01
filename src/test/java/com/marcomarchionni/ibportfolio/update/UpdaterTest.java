package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.services.FlexStatementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdaterTest {

    @Mock
    FlexStatementService flexStatementService;

    @Autowired
    Updater updater;

    @Test
    void detectGapTest() {

        List<FlexStatement> fls = new ArrayList<>();
        fls.add(FlexStatement.builder()
                .fromDate(LocalDate.of(2020, 1, 1))
                .toDate(LocalDate.of(2020,2,1)).build());
        fls.add(FlexStatement.builder()
                .fromDate(LocalDate.of(2020, 1, 15))
                .toDate(LocalDate.of(2020,2,15)).build());
        fls.add(FlexStatement.builder()
                .fromDate(LocalDate.of(2020, 3, 15))
                .toDate(LocalDate.of(2020,4,15)).build());

        when(flexStatementService.getAllOrderedByFromDateAsc()).thenReturn(fls);

        List<Gap> gaps = updater.detectGaps();

        assert (gaps.size() > 0);


    }
}