package com.ecommerce.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShippingCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingCalculatorApplication.class, args);
    }

}
