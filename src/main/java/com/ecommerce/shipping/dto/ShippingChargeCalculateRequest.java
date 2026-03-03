package com.ecommerce.shipping.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShippingChargeCalculateRequest {
    @NotNull(message = "sellerId cannot be null")
    @Min(value = 1, message = "Seller ID must be valid")
    private Long sellerId;

    @NotNull(message = "customerId cannot be null")
    @Min(value = 1, message = "Customer ID must be valid")
    private Long customerId;

    @NotBlank(message = "Delivery speed is required")
    private String deliverySpeed;

    @Min(value = 1, message = "Product ID must be valid and positive")
    private Long productId;

    // Optional weight parameter. If not provided, assumed 1.0 kg for calculation.
    @Min(value = 0, message = "Weight cannot be negative")
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
