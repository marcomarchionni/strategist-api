package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import com.marcomarchionni.ibportfolio.services.TradeService;
import com.marcomarchionni.ibportfolio.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;
    private final UserService userService;

    @GetMapping
    public List<TradeSummaryDto> findByFilter(@Valid TradeFindDto tradeFind) {
        User user = userService.getAuthenticatedUser();
        return tradeService.findByFilter(user, tradeFind);
    }

    @PutMapping
    public TradeSummaryDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto tradeUpdate) {
        User user = userService.getAuthenticatedUser();
        return tradeService.updateStrategyId(user, tradeUpdate);
    }
}
