package com.ecommerce.shipping.dto;

import jakarta.validation.constraints.NotNull;

public class ShippingChargeCalculateRequest {
    @NotNull(message = "sellerId cannot be null")
    private Long sellerId;

    @NotNull(message = "customerId cannot be null")
    private Long customerId;

    @NotNull(message = "deliverySpeed cannot be null")
    private String deliverySpeed;

    private Long productId;

    // Optional weight parameter. If not provided, assumed 1.0 kg for calculation.
    private Double weight;

    public ShippingChargeCalculateRequest() {
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(String deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
