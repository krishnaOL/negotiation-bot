-- data.sql

INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Toyota', 'Camry', 2022, 15000.0, false, ' I wish to sell a vehicle at the best feasible price, which is as follows.Sell at a price that is higher than the asking price or Evaluate whether the selling price falls within the range of 5% to 7% plus or minus. Notify me if the price falls below 7% by 2%. If the requested price is less than 10% of the listed price, please provide alternative vehicles that may fit the budget. Alternately, offer a free six-month or two-month warranty extension and see if the customer will agree to the listed price plus or minus 5%. ');
INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Honda', 'Accord', 2021, 16000.0, false, 'The car is listed at $16,000. The seller is open to negotiating within a reasonable range, but any offer below $16,000 will be politely declined. Additionally, the seller is willing to consider including an extended warranty if the offer is within $16,000 to $20,000. The car has a mileage of approximately 40,000 miles. ');
INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Mercedes', 'Maybach', 2020, 18000.0, false, 'The car is listed at $18,000. The seller is open to negotiating within a reasonable range, but any offer below $17,000 will be politely declined. Additionally, the seller is willing to consider including an extended warranty if the offer is within $17,000 to $20,000. The car has a mileage of approximately 40,000 miles. ');

INSERT INTO car_user (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO car_user (name, email) VALUES ('Jane Smith', 'jane@example.com');
INSERT INTO car_user (name, email) VALUES ('Sam Pett', 'sam@example.com');

INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (1, 1, 15500.0,16500.0, 'PENDING');
INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (2, 2, 16500.0,17500.0, 'PENDING');
