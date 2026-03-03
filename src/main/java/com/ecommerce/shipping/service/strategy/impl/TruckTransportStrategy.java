package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import org.springframework.stereotype.Component;

@Component
public class TruckTransportStrategy implements TransportModeStrategy {
    @Override
    public boolean isApplicable(double distanceKm) {
        return distanceKm > 100 && distanceKm <= 500;
    }

    @Override
    public double calculateTransportCharge(double distanceKm, double weightKg) {
        return 2.0 * distanceKm * weightKg;
    }

    @Override
    public String getModeName() {
        return "TRUCK";
    }
}
