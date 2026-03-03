# B2B E-Commerce Shipping Charge Estimator

Hey there! Thanks for reviewing my assignment. This repository contains the code for the B2B Shipping Charge Estimator API. I've built this taking inspiration from how platforms like Amazon and Flipkart handle logistics, while keeping it focused on the unique needs of a B2B Kirana supply chain.

## Tech Stack
* **Java 17** 
* **Spring Boot 3.4.0**
* **H2 Database** (for quick setup and mock data storage)
* **Maven** for build management
* **JUnit 5 / Mockito** for testing

## How to Run It
I wanted to make this as easy to review as possible, so the database is in-memory and spins up automatically.

1. **Clone/Unzip** the repository.
2. Open your terminal in the project root directory.
3. Run the tests to make sure everything is green:
   ```bash
   ./mvnw clean test
   ```
4. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```
The app will start on port `8080`.

## Design Choices & Architecture
I tried to keep the code clean, modular, and easy to extend. Here are a few key decisions:

1. **Strategy Pattern for Pricing Models:** 
   I used the Strategy Design Pattern for both `TransportModeStrategy` (Aeroplane, Truck, Mini Van) and `DeliverySpeedStrategy` (Standard, Express). This makes it incredibly easy to add new transport modes (like "Drone" or "Train") or new delivery speeds later without touching the core shipping calculation logic.

2. **Haversine Formula for Distance:**
   Instead of basic math, I implemented the Haversine formula in a utility class (`DistanceCalculator`). It calculates the real-world distance between two geographic coordinates (latitude and longitude) accounting for the Earth's curvature.

3. **In-Memory Mock Data:**
   I used an H2 database and a `data.sql` script to automatically seed the database on startup. It includes sample Customers, Sellers, Products, and Warehouses based on the assignment description so you can test the APIs right away.

4. **Global Exception Handling:**
   I added a `GlobalExceptionHandler` using `@ControllerAdvice`. If an API receives bad input (like a missing seller ID or an invalid product), it returns a clean, structured JSON error response instead of throwing an ugly stack trace.

5. **Caching:** 
   Finding the nearest warehouse across the entire country could get expensive as the platform grows. To reduce Haversine computation overhead for frequent lookups, I added Spring's `@Cacheable` to the warehouse lookup service. I also utilized a **Custom Cache Key**, ensuring that if a new warehouse is added nearby, the cache clears and a fresh geographic query is correctly run!

## API Endpoints

Once the app is running, you can hit these endpoints directly or use Postman.

### 1. Get Nearest Warehouse
Finds the closest drop-off point for a seller.
**GET** `http://localhost:8080/api/v1/warehouse/nearest?sellerId=123&productId=456`

### 2. Calculate Shipping Charge
Calculates the exact shipping cost based on distance and weight.
**GET** `http://localhost:8080/api/v1/shipping-charge?warehouseId=789&customerId=456&deliverySpeed=standard&weight=1.0`

### 3. Combined Shipping Estimate (Nearest + Calculate)
Combines the previous two steps into one seamless API call.
**POST** `http://localhost:8080/api/v1/shipping-charge/calculate`

**Body (JSON):**
```json
{
  "sellerId": 123,
  "customerId": 456,
  "deliverySpeed": "express",
  "weight": 5.0
}
```
Thanks again for the opportunity to work on this, it was a fun challenge! Let me know if you have any questions about the implementation.
