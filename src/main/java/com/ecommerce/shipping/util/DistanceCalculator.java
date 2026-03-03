package com.ecommerce.shipping.util;

import com.ecommerce.shipping.model.Location;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {

    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Calculates the distance between two locations in kilometers using the Haversine formula.
     */
    public double calculateDistance(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) {
            throw new IllegalArgumentException("Locations cannot be null");
        }

        double latDistance = Math.toRadians(loc2.getLat() - loc1.getLat());
        double lonDistance = Math.toRadians(loc2.getLng() - loc1.getLng());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(loc1.getLat())) * Math.cos(Math.toRadians(loc2.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
