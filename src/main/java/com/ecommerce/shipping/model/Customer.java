package com.ecommerce.shipping.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @Embedded
    private Location location;

    public Customer() {}

    public Customer(String name, String phone, Location location) {
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}
