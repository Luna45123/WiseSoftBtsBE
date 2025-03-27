package com.wisesoft.btsfare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wisesoft.btsfare.dto.BtsFareDTO;
import com.wisesoft.btsfare.dto.DiscountDTO;
import com.wisesoft.btsfare.dto.ExtensionFareDTO;
import com.wisesoft.btsfare.service.DiscountService;
import com.wisesoft.btsfare.service.ExtensionFareService;
import com.wisesoft.btsfare.service.FareService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final FareService fareService;
    private final DiscountService discountService;
    private final ExtensionFareService extensionFareService;

    public AdminController(FareService fareService, DiscountService discountService,
                           ExtensionFareService extensionFareService) {
        this.fareService = fareService;
        this.discountService = discountService;
        this.extensionFareService = extensionFareService;
    }

    @PostMapping("/update")
    public void updateFare(@RequestBody List<BtsFareDTO> fare) {
        fareService.updateFare(fare);
    }

    @GetMapping("/getAllFare")
    public ResponseEntity<List<BtsFareDTO>> getAllFare() {
        List<BtsFareDTO> fares = fareService.getAllBtsFare();
        return ResponseEntity.ok(fares);
    }

    @GetMapping("/getAllDiscount")
    public ResponseEntity<List<DiscountDTO>> getDiscounts() {
        List<DiscountDTO> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/extension-fare")
    public ResponseEntity<List<ExtensionFareDTO>> getExtensionFares() {
        List<ExtensionFareDTO> fares = extensionFareService.getAllExtensionFares();
        return ResponseEntity.ok(fares);
    }

    @PostMapping("/extension-fare-update")
    public void updateExtensionFare(@RequestBody List<ExtensionFareDTO> extensionFareDTO) {
        extensionFareService.updateExtensionFare(extensionFareDTO);
    }

    @PostMapping("/update-discount")
    public void updateDiscount(@RequestBody List<DiscountDTO> discountDTOs) {
        discountService.updateDiscount(discountDTOs);
    }
}
