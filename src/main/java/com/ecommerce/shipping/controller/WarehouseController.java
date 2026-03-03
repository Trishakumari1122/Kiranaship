package com.ecommerce.shipping.controller;

import com.ecommerce.shipping.dto.NearestWarehouseResponse;
import com.ecommerce.shipping.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<NearestWarehouseResponse> getNearestWarehouse(
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(warehouseService.getNearestWarehouse(sellerId, productId));
    }
}
