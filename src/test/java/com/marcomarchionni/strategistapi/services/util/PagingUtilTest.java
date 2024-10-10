package com.marcomarchionni.strategistapi.services.util;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PagingUtilTest {

    // MethodSource for providing test arguments
    private static Stream<Arguments> providePagingData() {
        return Stream.of(
                // skip, top, orderBy, expectedPageNumber, expectedPageSize, isSorted, direction, sortField
                Arguments.of(0, 10, "", 0, 10, false, null, null), // No sorting
                Arguments.of(20, 10, "name asc", 2, 10, true, Sort.Direction.ASC, "name"), // Ascending order
                Arguments.of(30, 10, "createdAt desc", 3, 10, true, Sort.Direction.DESC, "createdAt"), // Descending
                // order
                Arguments.of(15, 5, null, 3, 5, false, null, null), // No sorting, null orderBy
                Arguments.of(0, 10, "name", 0, 10, true, Sort.Direction.ASC, "name") // Ascending by default
        );
    }

    @ParameterizedTest
    @MethodSource("providePagingData")
    void testCreatePageable(int skip, int top, String orderBy, int expectedPageNumber, int expectedPageSize,
                            boolean isSorted, Sort.Direction direction, String sortField) {
        // Arrange
        var findAllReq = FindAllReq.builder().skip(skip).top(top).orderBy(orderBy).build();

        // Act
        Pageable pageable = PagingUtil.createPageable(findAllReq);

        // Assert
        assertEquals(expectedPageNumber, pageable.getPageNumber());
        assertEquals(expectedPageSize, pageable.getPageSize());

        if (isSorted) {
            assertTrue(pageable.getSort().isSorted());
            assertEquals(direction, Objects.requireNonNull(pageable.getSort().getOrderFor(sortField)).getDirection());
        } else {
            assertFalse(pageable.getSort().isSorted());
        }
    }
}