package com.ecommerce.shipping.util;

import com.ecommerce.shipping.model.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    private final DistanceCalculator calculator = new DistanceCalculator();

    @Test
    void testCalculateDistance_SameLocation() {
        Location loc1 = new Location(12.999, 37.999);
        double distance = calculator.calculateDistance(loc1, loc1);
        assertEquals(0.0, distance, 0.01);
    }

    @Test
    void testCalculateDistance_DifferentLocations() {
        Location bangalore = new Location(12.9716, 77.5946);
        Location mumbai = new Location(19.0760, 72.8777);
        double distance = calculator.calculateDistance(bangalore, mumbai);
        
        // Approximate distance between BLR and BOM is ~840-850 km
        assertTrue(distance > 800 && distance < 900, "Distance should be around 845 km");
    }

    @Test
    void testCalculateDistance_NullLocation_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDistance(null, new Location(0,0)));
    }
}
