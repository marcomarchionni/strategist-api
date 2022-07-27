package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.dtos.PositionCriteriaDto;
import com.marcomarchionni.ibportfolio.services.PositionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<Position> findWithCriteria(@Valid PositionCriteriaDto positionCriteria) {

        return positionService.findWithCriteria(positionCriteria);
    }

    @PutMapping
    public Position updateStrategyId(@RequestBody Position position) {
        return positionService.updateStrategyId(position);
    }


}
