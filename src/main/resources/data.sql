-- Mock Data
-- Customers
INSERT INTO customer (id, name, phone, lat, lng) VALUES (123, 'Shree Kirana Store', '9847111111', 11.232, 23.445495);
INSERT INTO customer (id, name, phone, lat, lng) VALUES (124, 'Andheri Mini Mart', '9101111111', 17.232, 33.445495);
INSERT INTO customer (id, name, phone, lat, lng) VALUES (456, 'Test Customer 456', '9999999999', 12.000, 30.000000);

-- Sellers
INSERT INTO seller (id, name, lat, lng) VALUES (1, 'Nestle Seller', 12.500, 36.500);
INSERT INTO seller (id, name, lat, lng) VALUES (2, 'Rice Seller', 14.500, 29.500);
INSERT INTO seller (id, name, lat, lng) VALUES (3, 'Sugar Seller', 16.500, 34.500);
INSERT INTO seller (id, name, lat, lng) VALUES (123, 'Test Seller 123', 13.000, 37.000); -- For API Sample Request

-- Products
-- Maggie 500g: 0.5kg, 10x10x10. Price: 10
INSERT INTO product (id, name, price, weight_in_kg, length_cm, width_cm, height_cm, is_bulk_item, is_fragile, seller_id) VALUES (1, 'Maggie 500g Packet', 10.0, 0.5, 10, 10, 10, false, true, 1);
-- Rice Bag 10Kg
INSERT INTO product (id, name, price, weight_in_kg, length_cm, width_cm, height_cm, is_bulk_item, is_fragile, seller_id) VALUES (2, 'Rice Bag 10Kg', 500.0, 10.0, 1000, 800, 500, true, false, 2);
-- Sugar Bag 25kg: 25kg, 1000x900x600
INSERT INTO product (id, name, price, weight_in_kg, length_cm, width_cm, height_cm, is_bulk_item, is_fragile, seller_id) VALUES (3, 'Sugar Bag 25kg', 700.0, 25.0, 1000, 900, 600, true, false, 3);
-- Test Product 456
INSERT INTO product (id, name, price, weight_in_kg, length_cm, width_cm, height_cm, is_bulk_item, is_fragile, seller_id) VALUES (456, 'Test Product 456', 100.0, 1.0, 10, 10, 10, false, false, 123);

-- Warehouses
INSERT INTO warehouse (id, name, lat, lng) VALUES (1, 'BLR_Warehouse', 12.99999, 37.923273);
INSERT INTO warehouse (id, name, lat, lng) VALUES (2, 'MUMB_Warehouse', 11.99999, 27.923273);
INSERT INTO warehouse (id, name, lat, lng) VALUES (789, 'Warehouse_789', 12.99999, 37.923273); -- For API Sample Request
