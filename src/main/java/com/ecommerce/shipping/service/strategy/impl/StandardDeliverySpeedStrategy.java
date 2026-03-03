package com.ecommerce.shipping.service.strategy.impl;

import com.ecommerce.shipping.service.strategy.DeliverySpeedStrategy;
import org.springframework.stereotype.Component;

@Component
public class StandardDeliverySpeedStrategy implements DeliverySpeedStrategy {
    @Override
    public boolean isApplicable(String speedType) {
        return "standard".equalsIgnoreCase(speedType);
    }

    @Override
    public double calculateSpeedCharge(double weightKg) {
        return 10.0; // Rs 10 standard courier charge
    }

    @Override
    public String getSpeedName() {
        return "STANDARD";
    }
}
