package com.ecommerce.shipping.service;

import com.ecommerce.shipping.dto.CombinedShippingChargeResponse;
import com.ecommerce.shipping.dto.NearestWarehouseResponse;
import com.ecommerce.shipping.dto.ShippingChargeCalculateRequest;
import com.ecommerce.shipping.model.Customer;
import com.ecommerce.shipping.model.Warehouse;
import com.ecommerce.shipping.repository.CustomerRepository;
import com.ecommerce.shipping.repository.ProductRepository;
import com.ecommerce.shipping.repository.WarehouseRepository;
import com.ecommerce.shipping.service.strategy.DeliverySpeedStrategy;
import com.ecommerce.shipping.service.strategy.TransportModeStrategy;
import com.ecommerce.shipping.util.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingService {

        private final WarehouseRepository warehouseRepository;
        private final CustomerRepository customerRepository;
        private final ProductRepository productRepository;
        private final WarehouseService warehouseService;
        private final DistanceCalculator distanceCalculator;
        private final Map<String, TransportModeStrategy> transportModeStrategies;
        private final Map<String, DeliverySpeedStrategy> deliverySpeedStrategies;

        public ShippingService(WarehouseRepository warehouseRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        WarehouseService warehouseService,
                        DistanceCalculator distanceCalculator,
                        List<TransportModeStrategy> transportModeStrategies,
                        List<DeliverySpeedStrategy> deliverySpeedStrategies) {
                this.warehouseRepository = warehouseRepository;
                this.customerRepository = customerRepository;
                this.productRepository = productRepository;
                this.warehouseService = warehouseService;
                this.distanceCalculator = distanceCalculator;
                this.transportModeStrategies = transportModeStrategies.stream()
                                .collect(java.util.stream.Collectors.toMap(s -> s.getModeName().toUpperCase(), s -> s));
                this.deliverySpeedStrategies = deliverySpeedStrategies.stream()
                                .collect(java.util.stream.Collectors.toMap(s -> s.getSpeedName().toUpperCase(),
                                                s -> s));
        }

        private String determineTransportMode(double distanceKm) {
                if (distanceKm <= 100)
                        return "MINIVAN";
                if (distanceKm <= 500)
                        return "TRUCK";
                return "AEROPLANE";
        }

        public double calculateShippingCharge(Long warehouseId, Long customerId, String deliverySpeed,
                        Double weightKg, Long productId) {
                Warehouse warehouse = warehouseRepository.findById(warehouseId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Warehouse not found with ID: " + warehouseId));
                Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Customer not found with ID: " + customerId));

                double actualWeight = weightKg != null ? weightKg : 1.0; // Default to 1.0kg if not specified
                double distance = distanceCalculator.calculateDistance(warehouse.getLocation(), customer.getLocation());

                String modeName = determineTransportMode(distance);

                if (actualWeight >= 50.0) {
                        modeName = "HEAVYTRUCK";
                } else if (productId != null) {
                        com.ecommerce.shipping.model.Product product = productRepository.findById(productId)
                                        .orElse(null);
                        if (product != null && product.isBulkItem()) {
                                modeName = "HEAVYTRUCK";
                        }
                }
                TransportModeStrategy transportStrategy = transportModeStrategies.get(modeName);
                if (transportStrategy == null) {
                        throw new IllegalStateException("No applicable transport mode found for distance: " + distance);
                }

                DeliverySpeedStrategy speedStrategy = deliverySpeedStrategies.get(deliverySpeed.toUpperCase());
                if (speedStrategy == null) {
                        throw new IllegalArgumentException("Invalid or unsupported delivery speed: " + deliverySpeed);
                }

                double transportCharge = transportStrategy.calculateTransportCharge(distance, actualWeight);
                double speedCharge = speedStrategy.calculateSpeedCharge(actualWeight);

                return transportCharge + speedCharge;
        }

        public CombinedShippingChargeResponse calculateCombinedShippingCharge(ShippingChargeCalculateRequest request) {
                NearestWarehouseResponse nearestWarehouse = warehouseService
                                .getNearestWarehouseBySeller(request.getSellerId());

                double charge = calculateShippingCharge(nearestWarehouse.getWarehouseId(), request.getCustomerId(),
                                request.getDeliverySpeed(), request.getWeight(), request.getProductId());

                return new CombinedShippingChargeResponse(charge, nearestWarehouse);
        }
}
