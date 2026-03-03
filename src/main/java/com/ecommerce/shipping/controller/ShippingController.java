package com.ecommerce.shipping.controller;

import com.ecommerce.shipping.dto.CombinedShippingChargeResponse;
import com.ecommerce.shipping.dto.ShippingChargeCalculateRequest;
import com.ecommerce.shipping.dto.ShippingChargeResponse;
import com.ecommerce.shipping.service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping-charge")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping
    public ResponseEntity<ShippingChargeResponse> getShippingCharge(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("customerId") Long customerId,
            @RequestParam("deliverySpeed") String deliverySpeed,
            @RequestParam(value = "weight", required = false) Double weight) {

        double charge = shippingService.calculateShippingCharge(warehouseId, customerId, deliverySpeed, weight);
        return ResponseEntity.ok(new ShippingChargeResponse(charge));
    }

    @PostMapping("/calculate")
    public ResponseEntity<CombinedShippingChargeResponse> calculateCombinedShippingCharge(
            @Valid @RequestBody ShippingChargeCalculateRequest request) {

        return ResponseEntity.ok(shippingService.calculateCombinedShippingCharge(request));
    }
}
