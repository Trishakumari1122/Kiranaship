package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import org.springframework.stereotype.Component;

@Component
public class MiniVanTransportStrategy implements TransportModeStrategy {
    @Override
    public boolean isApplicable(double distanceKm) {
        return distanceKm >= 0 && distanceKm <= 100;
    }

    @Override
    public double calculateTransportCharge(double distanceKm, double weightKg) {
        return 3.0 * distanceKm * weightKg;
    }
}
