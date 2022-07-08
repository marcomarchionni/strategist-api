package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.services.FlexStatementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdaterTest {


    @Mock
    FlexStatementService flexStatementServiceMock;

    @InjectMocks
    Updater updater;

    @Test
    void detectGapIfGapTest() {

        //setup
        List<FlexStatement> fls = new ArrayList<>();
        FlexStatement fl1 = new FlexStatement();
        fl1.setFromDate(LocalDate.of(2020, 1, 1));
        fl1.setToDate(LocalDate.of(2020,2,1));
        FlexStatement fl2 = new FlexStatement();
        fl2.setFromDate(LocalDate.of(2020, 1, 15));
        fl2.setToDate(LocalDate.of(2020,3,15));
        FlexStatement fl3 = new FlexStatement();
        fl3.setFromDate(LocalDate.of(2020, 6, 15));
        fl3.setToDate(LocalDate.of(2020,8,15));
        fls.add(fl1);
        fls.add(fl2);
        fls.add(fl3);

        when(flexStatementServiceMock.findAllOrderedByFromDateAsc()).thenReturn(fls);

        List<TimeInterval> timeIntervals = ReflectionTestUtils.invokeMethod(updater, "detectDataGaps");

        verify(flexStatementServiceMock).findAllOrderedByFromDateAsc();

        assertNotNull(timeIntervals);
        assertTrue(timeIntervals.size() > 0);
        assertEquals(timeIntervals.size(), 1);
        assertEquals(LocalDate.of(2020, 3, 16), timeIntervals.get(0).getFromDate());
        assertEquals(LocalDate.of(2020, 6, 14), timeIntervals.get(0).getToDate());

    }

    @Test
    void detectGapIfNoGapTest() {

        //setup
        List<FlexStatement> fls = new ArrayList<>();
        FlexStatement fl1 = new FlexStatement();
        fl1.setFromDate(LocalDate.of(2020, 1, 1));
        fl1.setToDate(LocalDate.of(2020,2,1));
        FlexStatement fl2 = new FlexStatement();
        fl2.setFromDate(LocalDate.of(2020, 1, 15));
        fl2.setToDate(LocalDate.of(2020,2,15));
        FlexStatement fl3 = new FlexStatement();
        fl3.setFromDate(LocalDate.of(2020, 2, 16));
        fl3.setToDate(LocalDate.of(2020,4,15));
        fls.add(fl1);
        fls.add(fl2);
        fls.add(fl3);

        when(flexStatementServiceMock.findAllOrderedByFromDateAsc()).thenReturn(fls);

        List<TimeInterval> timeIntervals = ReflectionTestUtils.invokeMethod(updater, "detectDataGaps");

        verify(flexStatementServiceMock).findAllOrderedByFromDateAsc();
        assertNotNull(timeIntervals);
        assertFalse(timeIntervals.size() > 0);
    }
}