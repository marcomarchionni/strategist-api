package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dividends")
public class DividendController {

    private final DividendService dividendService;
    private final UserService userService;

    @GetMapping
    public List<DividendSummaryDto> findByFilter(@Valid DividendFindDto dividendFind) {
        User user = userService.getAuthenticatedUser();
        return dividendService.findByFilter(user, dividendFind);
    }

    @PutMapping
    public DividendSummaryDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto dividendUpdate) {
        User user = userService.getAuthenticatedUser();
        return dividendService.updateStrategyId(user, dividendUpdate);
    }
}
