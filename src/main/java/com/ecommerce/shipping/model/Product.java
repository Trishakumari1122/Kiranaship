package com.ecommerce.shipping.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private double weightInKg;
    
    // Storing volume or dimensions
    private double lengthCm;
    private double widthCm;
    private double heightCm;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Product() {}

    public Product(String name, double price, double weightInKg, double lengthCm, double widthCm, double heightCm, Seller seller) {
        this.name = name;
        this.price = price;
        this.weightInKg = weightInKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.seller = seller;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getWeightInKg() { return weightInKg; }
    public void setWeightInKg(double weightInKg) { this.weightInKg = weightInKg; }
    public double getLengthCm() { return lengthCm; }
    public void setLengthCm(double lengthCm) { this.lengthCm = lengthCm; }
    public double getWidthCm() { return widthCm; }
    public void setWidthCm(double widthCm) { this.widthCm = widthCm; }
    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }
    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
}
