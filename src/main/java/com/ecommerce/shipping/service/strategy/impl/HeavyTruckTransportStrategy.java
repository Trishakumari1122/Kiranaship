package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import org.springframework.stereotype.Component;

@Component
public class HeavyTruckTransportStrategy implements TransportModeStrategy {
    @Override
    public boolean isApplicable(double distanceKm) {
        // We override this dynamically via the Factory map so this is irrelevant.
        return true;
    }

    @Override
    public double calculateTransportCharge(double distanceKm, double weightKg) {
        // Heavy trucks give a bulk discount rate explicitly for heavy bulk kirana
        // orders
        // Standard truck is 2.0 Rs/km/kg. Heavy truck drops it to 0.8 Rs/km/kg to be
        // friendly
        // plus a fixed load handling fee
        return (0.8 * distanceKm * weightKg) + 500.0;
    }

    @Override
    public String getModeName() {
        return "HEAVYTRUCK";
    }
}
