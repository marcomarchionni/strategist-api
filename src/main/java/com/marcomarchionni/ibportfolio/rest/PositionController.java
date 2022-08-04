package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.model.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;
import com.marcomarchionni.ibportfolio.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<PositionListDto> findByFilter(@Valid PositionFindDto positionFind) {
        return positionService.findByFilter(positionFind);
    }

    @PutMapping
    public PositionListDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto positionUpdate) {
        return positionService.updateStrategyId(positionUpdate);
    }
}
