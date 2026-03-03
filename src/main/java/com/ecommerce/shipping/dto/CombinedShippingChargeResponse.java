package com.ecommerce.shipping.dto;

public class CombinedShippingChargeResponse {
    private double shippingCharge;
    private NearestWarehouseResponse nearestWarehouse;

    public CombinedShippingChargeResponse() {}

    public CombinedShippingChargeResponse(double shippingCharge, NearestWarehouseResponse nearestWarehouse) {
        this.shippingCharge = shippingCharge;
        this.nearestWarehouse = nearestWarehouse;
    }

    public double getShippingCharge() { return shippingCharge; }
    public void setShippingCharge(double shippingCharge) { this.shippingCharge = shippingCharge; }
    public NearestWarehouseResponse getNearestWarehouse() { return nearestWarehouse; }
    public void setNearestWarehouse(NearestWarehouseResponse nearestWarehouse) { this.nearestWarehouse = nearestWarehouse; }
}
