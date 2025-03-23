package com.wisesoft.btsfare.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<BtsFareDTO> getAllFare() {
        return fareService.getAllBtsFare();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllDiscount")
    public List<DiscountDTO> getDiscounts() {
        return discountService.getAllDiscounts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/extension-fare")
    public List<ExtensionFareDTO> updateExtenstionFare() {
        return extensionFareService.getAllExtensionFares();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/extension-fare-update")
    public String updateExtensionFare(@RequestBody List<ExtensionFareDTO> extensionFareDTO) {
        return extensionFareService.updateExtensionFare(extensionFareDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update-discount")
    public String updateDiscount(@RequestBody List<DiscountDTO> discountDTOs) {
        return discountService.updateDiscount(discountDTOs);
    }
}
