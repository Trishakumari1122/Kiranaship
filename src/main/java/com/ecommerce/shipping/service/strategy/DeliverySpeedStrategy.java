package com.ecommerce.shipping.service.strategy;

public interface DeliverySpeedStrategy {
    boolean isApplicable(String speedType);
    double calculateSpeedCharge(double weightKg);
}
