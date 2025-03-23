package com.wisesoft.btsfare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<String> updateFare(@RequestBody List<BtsFareDTO> fare) {
        fareService.updateFare(fare);
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllFare")
    public ResponseEntity<List<BtsFareDTO>> getAllFare() {
        List<BtsFareDTO> fares = fareService.getAllBtsFare();
        return ResponseEntity.ok(fares);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllDiscount")
    public ResponseEntity<List<DiscountDTO>> getDiscounts() {
        List<DiscountDTO> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/extension-fare")
    public ResponseEntity<List<ExtensionFareDTO>> getExtensionFares() {
        List<ExtensionFareDTO> fares = extensionFareService.getAllExtensionFares();
        return ResponseEntity.ok(fares);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/extension-fare-update")
    public ResponseEntity<String> updateExtensionFare(@RequestBody List<ExtensionFareDTO> extensionFareDTO) {
        String result = extensionFareService.updateExtensionFare(extensionFareDTO);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update-discount")
    public ResponseEntity<String> updateDiscount(@RequestBody List<DiscountDTO> discountDTOs) {
        String result = discountService.updateDiscount(discountDTOs);
        return ResponseEntity.ok(result);
    }
}
