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

@Service
public class ShippingService {

        private final WarehouseRepository warehouseRepository;
        private final CustomerRepository customerRepository;
        private final ProductRepository productRepository;
        private final WarehouseService warehouseService;
        private final DistanceCalculator distanceCalculator;
        private final List<TransportModeStrategy> transportModeStrategies;
        private final List<DeliverySpeedStrategy> deliverySpeedStrategies;

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
                this.transportModeStrategies = transportModeStrategies;
                this.deliverySpeedStrategies = deliverySpeedStrategies;
        }

        public double calculateShippingCharge(Long warehouseId, Long customerId, String deliverySpeed,
                        Double weightKg) {
                Warehouse warehouse = warehouseRepository.findById(warehouseId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Warehouse not found with ID: " + warehouseId));
                Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Customer not found with ID: " + customerId));

                double actualWeight = weightKg != null ? weightKg : 1.0; // Default to 1.0kg if not specified
                double distance = distanceCalculator.calculateDistance(warehouse.getLocation(), customer.getLocation());

                TransportModeStrategy transportStrategy = transportModeStrategies.stream()
                                .filter(s -> s.isApplicable(distance))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException(
                                                "No applicable transport mode found for distance: " + distance));

                DeliverySpeedStrategy speedStrategy = deliverySpeedStrategies.stream()
                                .filter(s -> s.isApplicable(deliverySpeed))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Invalid or unsupported delivery speed: " + deliverySpeed));

                double transportCharge = transportStrategy.calculateTransportCharge(distance, actualWeight);
                double speedCharge = speedStrategy.calculateSpeedCharge(actualWeight);

                return transportCharge + speedCharge;
        }

        public CombinedShippingChargeResponse calculateCombinedShippingCharge(ShippingChargeCalculateRequest request) {
                NearestWarehouseResponse nearestWarehouse = warehouseService
                                .getNearestWarehouseBySeller(request.getSellerId());

                double charge = calculateShippingCharge(nearestWarehouse.getWarehouseId(), request.getCustomerId(),
                                request.getDeliverySpeed(), request.getWeight());

                return new CombinedShippingChargeResponse(charge, nearestWarehouse);
        }
}
