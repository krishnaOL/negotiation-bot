-- data.sql

INSERT INTO car (make, model, car_year, min_bid_amount, sold_out) VALUES ('Toyota', 'Camry', 2022, 15000.0, false);
INSERT INTO car (make, model, car_year, min_bid_amount, sold_out) VALUES ('Honda', 'Accord', 2021, 16000.0, false);

INSERT INTO car_user (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO car_user (name, email) VALUES ('Jane Smith', 'jane@example.com');

INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (1, 1, 15500.0,16500.0, 'PENDING');
INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (2, 2, 16500.0,17500.0, 'PENDING');
