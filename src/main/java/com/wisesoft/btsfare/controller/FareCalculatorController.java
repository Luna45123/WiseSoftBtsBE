package com.wisesoft.btsfare.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisesoft.btsfare.dto.StationDTO;
import com.wisesoft.btsfare.modal.PriceResultBTS;
import com.wisesoft.btsfare.service.CalculateFareService;
import com.wisesoft.btsfare.service.StationService;

@RestController
@RequestMapping("/fare")
public class FareCalculatorController {

    private final CalculateFareService calculateFareService;
    private final StationService stationService;

    public FareCalculatorController(CalculateFareService calculateFareService, StationService stationService) {
        this.calculateFareService = calculateFareService;
        this.stationService = stationService;
    }

   

    @GetMapping("/getFare")
    public ResponseEntity<PriceResultBTS> getFare(@RequestParam String start, @RequestParam String end) {
        PriceResultBTS priceResultBTS = calculateFareService.calculateFare(start, end);
        return ResponseEntity.ok(priceResultBTS);
    }

    @GetMapping("/by-line")
    public ResponseEntity<List<StationDTO>> getStationsByLineName(@RequestParam String lineName) {
        return ResponseEntity.ok(stationService.getStationByLine(lineName));
    }


}