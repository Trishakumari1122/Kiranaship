package com.ecommerce.shipping.dto;

import com.ecommerce.shipping.model.Location;

public class NearestWarehouseResponse {
    private Long warehouseId;
    private Location warehouseLocation;

    public NearestWarehouseResponse() {}

    public NearestWarehouseResponse(Long warehouseId, Location warehouseLocation) {
        this.warehouseId = warehouseId;
        this.warehouseLocation = warehouseLocation;
    }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Location getWarehouseLocation() { return warehouseLocation; }
    public void setWarehouseLocation(Location warehouseLocation) { this.warehouseLocation = warehouseLocation; }
}
