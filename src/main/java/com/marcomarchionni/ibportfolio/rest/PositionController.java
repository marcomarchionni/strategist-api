package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.services.PositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {

    PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/positions")
    public List<Position> findWithParameters(@RequestParam (value = "tagged", required = false) Boolean tagged,
                                             @RequestParam (value = "symbol", required = false) String symbol,
                                             @RequestParam (value = "assetCategory", required = false) String assetCategory ) {

        return positionService.findWithParameters(tagged, symbol, assetCategory);
    }

    @PutMapping("/positions")
    public Position updateStrategyId(@RequestBody Position position) {
        return positionService.updateStrategyId(position);
    }


}
