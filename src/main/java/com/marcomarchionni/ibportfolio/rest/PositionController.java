package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.PositionFindDto;
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
    public List<Position> findByParams(@Valid PositionFindDto positionFind) {
        return positionService.findByParams(positionFind);
    }

    @PutMapping
    public Position updateStrategyId(@RequestBody @Valid UpdateStrategyDto positionUpdate) {
        return positionService.updateStrategyId(positionUpdate);
    }
}
