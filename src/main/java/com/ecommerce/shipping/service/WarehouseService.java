package com.ecommerce.shipping.service;

import com.ecommerce.shipping.dto.NearestWarehouseResponse;
import com.ecommerce.shipping.model.Seller;
import com.ecommerce.shipping.model.Warehouse;
import com.ecommerce.shipping.repository.ProductRepository;
import com.ecommerce.shipping.repository.SellerRepository;
import com.ecommerce.shipping.repository.WarehouseRepository;
import com.ecommerce.shipping.util.DistanceCalculator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final DistanceCalculator distanceCalculator;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            SellerRepository sellerRepository,
                            ProductRepository productRepository,
                            DistanceCalculator distanceCalculator) {
        this.warehouseRepository = warehouseRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.distanceCalculator = distanceCalculator;
    }

    @Cacheable("nearestWarehouse")
    public NearestWarehouseResponse getNearestWarehouse(Long sellerId, Long productId) {
        // Validate inputs
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with ID: " + sellerId));

        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        return findNearestWarehouseForSeller(seller);
    }

    public NearestWarehouseResponse getNearestWarehouseBySeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with ID: " + sellerId));

        return findNearestWarehouseForSeller(seller);
    }

    private NearestWarehouseResponse findNearestWarehouseForSeller(Seller seller) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new IllegalStateException("No warehouses available");
        }

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {
            double distance = distanceCalculator.calculateDistance(seller.getLocation(), warehouse.getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = warehouse;
            }
        }

        if (nearest == null) {
            throw new IllegalStateException("Could not determine nearest warehouse");
        }

        return new NearestWarehouseResponse(nearest.getId(), nearest.getLocation());
    }
}
