package com.ecommerce.shipping.service;

import com.ecommerce.shipping.model.Customer;
import com.ecommerce.shipping.model.Location;
import com.ecommerce.shipping.model.Warehouse;
import com.ecommerce.shipping.repository.CustomerRepository;
import com.ecommerce.shipping.repository.ProductRepository;
import com.ecommerce.shipping.repository.WarehouseRepository;
import com.ecommerce.shipping.service.strategy.DeliverySpeedStrategy;
import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import com.ecommerce.shipping.service.strategy.impl.*;
import com.ecommerce.shipping.util.DistanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ShippingServiceTest {

    private ShippingService shippingService;
    private WarehouseRepository warehouseRepository;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        warehouseRepository = Mockito.mock(WarehouseRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        WarehouseService warehouseService = Mockito.mock(WarehouseService.class);
        DistanceCalculator distanceCalculator = new DistanceCalculator();

        List<TransportModeStrategy> transportModeStrategies = Arrays.asList(
                new MiniVanTransportStrategy(),
                new TruckTransportStrategy(),
                new AeroplaneTransportStrategy(),
                new HeavyTruckTransportStrategy());

        List<DeliverySpeedStrategy> deliverySpeedStrategies = Arrays.asList(
                new StandardDeliverySpeedStrategy(),
                new ExpressDeliverySpeedStrategy());

        shippingService = new ShippingService(
                warehouseRepository,
                customerRepository,
                productRepository,
                warehouseService,
                distanceCalculator,
                transportModeStrategies,
                deliverySpeedStrategies);
    }

    @Test
    void testCalculateShippingCharge_StandardSpeed_MiniVan() {
        Warehouse warehouse = new Warehouse("W1", new Location(10.0, 10.0));
        Customer customer = new Customer("C1", "123", new Location(10.1, 10.0)); // ~11km distance

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Weight = 2kg
        // Distance ~ 11.119 km (under 100km -> MiniVan = 3 Rs/km/kg)
        // Transport = 3 * 11.119 * 2 = 66.714
        // Speed (Standard) = 10
        // Total = ~ 76.714

        double charge = shippingService.calculateShippingCharge(1L, 1L, "standard", 2.0, null);

        assertEquals(76.71, charge, 0.01);
    }

    @Test
    void testCalculateShippingCharge_ExpressSpeed_Aeroplane() {
        Warehouse warehouse = new Warehouse("W1", new Location(10.0, 10.0));
        Customer customer = new Customer("C1", "123", new Location(20.0, 20.0)); // ~1400km distance

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Weight = 5kg
        // Distance ~ 1494 km (over 500km -> Aeroplane = 1 Rs/km/kg)
        // Transport = 1 * 1494 * 5 = 7470
        // Speed (Express) = 10 + (1.2 * 5) = 16
        // Total = 7486

        double charge = shippingService.calculateShippingCharge(1L, 1L, "express", 5.0, null);

        assertEquals(7739.78, charge, 0.01);
    }

    @Test
    void testCalculateShippingCharge_HeavyTruck_BulkItem() {
        Warehouse warehouse = new Warehouse("W1", new Location(10.0, 10.0));
        Customer customer = new Customer("C1", "123", new Location(10.1, 10.0)); // ~11km distance

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Weight = 60kg (Triggers heavy truck >= 50kg)
        // Distance ~ 11.119 km
        // Heavy Truck Transport = (0.8 * 11.119 * 60) + 500 = 533.71 + 500 = 1033.71
        // Speed (Standard) = 10
        // Total = ~1043.71

        double charge = shippingService.calculateShippingCharge(1L, 1L, "standard", 60.0, null);
        assertEquals(1043.71, charge, 0.5);
    }
}
