package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.services.FlexInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdaterTest {


    @Mock
    FlexInfoService flexInfoServiceMock;

    @InjectMocks
    Updater updater;

    @Test
    void detectGapIfGapTest() {

        //setup
        List<FlexInfo> fls = new ArrayList<>();
        FlexInfo fl1 = new FlexInfo();
        fl1.setFromDate(LocalDate.of(2020, 1, 1));
        fl1.setToDate(LocalDate.of(2020,2,1));
        FlexInfo fl2 = new FlexInfo();
        fl2.setFromDate(LocalDate.of(2020, 1, 15));
        fl2.setToDate(LocalDate.of(2020,3,15));
        FlexInfo fl3 = new FlexInfo();
        fl3.setFromDate(LocalDate.of(2020, 6, 15));
        fl3.setToDate(LocalDate.of(2020,8,15));
        fls.add(fl1);
        fls.add(fl2);
        fls.add(fl3);

        when(flexInfoServiceMock.findAllOrderedByFromDateAsc()).thenReturn(fls);

        List<TimeInterval> timeIntervals = ReflectionTestUtils.invokeMethod(updater, "detectUndocumentedTimeIntervals");

        verify(flexInfoServiceMock).findAllOrderedByFromDateAsc();

        assertNotNull(timeIntervals);
        assertTrue(timeIntervals.size() > 0);
        assertEquals(timeIntervals.size(), 1);
        assertEquals(LocalDate.of(2020, 3, 16), timeIntervals.get(0).getFromDate());
        assertEquals(LocalDate.of(2020, 6, 14), timeIntervals.get(0).getToDate());

    }

    @Test
    void detectGapIfNoGapTest() {

        //setup
        List<FlexInfo> fls = new ArrayList<>();
        FlexInfo fl1 = new FlexInfo();
        fl1.setFromDate(LocalDate.of(2020, 1, 1));
        fl1.setToDate(LocalDate.of(2020,2,1));
        FlexInfo fl2 = new FlexInfo();
        fl2.setFromDate(LocalDate.of(2020, 1, 15));
        fl2.setToDate(LocalDate.of(2020,2,15));
        FlexInfo fl3 = new FlexInfo();
        fl3.setFromDate(LocalDate.of(2020, 2, 16));
        fl3.setToDate(LocalDate.of(2020,4,15));
        fls.add(fl1);
        fls.add(fl2);
        fls.add(fl3);

        when(flexInfoServiceMock.findAllOrderedByFromDateAsc()).thenReturn(fls);

        List<TimeInterval> timeIntervals = ReflectionTestUtils.invokeMethod(updater, "detectUndocumentedTimeIntervals");

        verify(flexInfoServiceMock).findAllOrderedByFromDateAsc();
        assertNotNull(timeIntervals);
        assertFalse(timeIntervals.size() > 0);
    }
}