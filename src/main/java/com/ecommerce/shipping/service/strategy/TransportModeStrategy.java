package com.ecommerce.shipping.service.strategy;

public interface TransportModeStrategy {
    boolean isApplicable(double distanceKm);

    double calculateTransportCharge(double distanceKm, double weightKg);

    String getModeName();
}
