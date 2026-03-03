package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import org.springframework.stereotype.Component;

@Component
public class AeroplaneTransportStrategy implements TransportModeStrategy {
    @Override
    public boolean isApplicable(double distanceKm) {
        return distanceKm > 500;
    }

    @Override
    public double calculateTransportCharge(double distanceKm, double weightKg) {
        return 1.0 * distanceKm * weightKg;
    }

    @Override
    public String getModeName() {
        return "AEROPLANE";
    }
}
