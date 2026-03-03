package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.DeliverySpeedStrategy;
import org.springframework.stereotype.Component;

@Component
public class ExpressDeliverySpeedStrategy implements DeliverySpeedStrategy {
    @Override
    public boolean isApplicable(String speedType) {
        return "express".equalsIgnoreCase(speedType);
    }

    @Override
    public double calculateSpeedCharge(double weightKg) {
        // Rs 10 standard + Rs 1.2 per kg extra
        return 10.0 + (1.2 * weightKg);
    }
}
