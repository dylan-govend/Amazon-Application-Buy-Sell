CREATE TABLE user_t(
    email_address VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT user_password_chk CHECK(LENGTH(user_password) >= 8 AND LENGTH(user_password) <= 255),
	CONSTRAINT contact_number_chk CHECK(LENGTH(contact_number) <= 20),
	CONSTRAINT first_name_chk CHECK(LENGTH(first_name) <= 255),
	CONSTRAINT last_name_chk CHECK(LENGTH(last_name) <= 255),
	
	#-- key constraint
	CONSTRAINT user_pk PRIMARY KEY(email_address)
);

CREATE TABLE address_book(
	address_book_id INT NOT NULL AUTO_INCREMENT,
	street_address VARCHAR(255) NOT NULL,
	suburb VARCHAR(200) NOT NULL,
	city_town VARCHAR(200) NOT NULL,
	province VARCHAR(200) NOT NULL,
	postal_code VARCHAR(10) NOT NULL,
	user_id VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT street_address_chk CHECK(LENGTH(street_address) <= 255),
	CONSTRAINT suburb_chk CHECK(LENGTH(suburb) <= 200),
	CONSTRAINT city_town_chk CHECK(LENGTH(city_town) <= 200),
	CONSTRAINT province_chk CHECK(LENGTH(province) <= 200),
	CONSTRAINT postal_code_chk CHECK(LENGTH(postal_code) <= 10),
		
	#-- key constraint
	CONSTRAINT address_book_pk PRIMARY KEY(address_book_id),
	CONSTRAINT address_book_fk FOREIGN KEY(user_id) REFERENCES user_t(email_address)
);

CREATE TABLE customer(
	customer_id VARCHAR(255) NOT NULL,
	date_time_signed_in DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	date_time_signed_out DATETIME DEFAULT(CURRENT_TIMESTAMP),
	
	#-- key constraint
	CONSTRAINT customer_pk PRIMARY KEY(customer_id),
    CONSTRAINT customer_fk1 FOREIGN KEY(customer_id) REFERENCES user_t(email_address)
);

CREATE TABLE cart(
	cart_id INT NOT NULL AUTO_INCREMENT,
	total_product_num INT NOT NULL DEFAULT(0),
	total_price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	customer_id VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT total_product_num_chk CHECK(total_product_num >= 0),
	CONSTRAINT total_price_chk CHECK(total_price >= 0.00),
	
	#-- key constraint
	CONSTRAINT cart_pk PRIMARY KEY(cart_id),
	CONSTRAINT cart_fk FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE wish_list(
	wish_list_id INT NOT NULL AUTO_INCREMENT,
	wish_list_title VARCHAR(60) NOT NULL DEFAULT('Wish List'),
	date_made DATE NOT NULL DEFAULT(CURRENT_DATE),
	customer_id VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT wish_list_title_chk CHECK(LENGTH(wish_list_title) >= 0 AND LENGTH(wish_list_title) <= 60),
	
	#-- key constraint
	CONSTRAINT wish_list_pk PRIMARY KEY(wish_list_id),
	CONSTRAINT wish_list_fk FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE order_t(
	order_number INT NOT NULL AUTO_INCREMENT,
	order_date DATE NOT NULL DEFAULT (CURRENT_DATE),
	order_total_price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	total_product_num INT NOT NULL DEFAULT(1),
	is_active BOOLEAN NOT NULL DEFAULT(TRUE),
	order_status VARCHAR(20) NOT NULL DEFAULT('Waiting for payment'),
	customer_id VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT order_total_price_chk CHECK(order_total_price >= 0.00),
	CONSTRAINT order_total_product_num_chk CHECK(total_product_num >= 1),
	CONSTRAINT order_status_chk CHECK(order_status IN('Waiting for payment', 'Processing order', 'Cancelled', 'Processed')),
	
	#-- key constraint
	CONSTRAINT order_pk PRIMARY KEY(order_number),
	CONSTRAINT order_fk FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE return_t(
	tracking_number INT NOT NULL AUTO_INCREMENT,
	return_date DATE NOT NULL DEFAULT(CURRENT_DATE),
	return_reason TEXT NOT NULL,
	return_status VARCHAR(50) NOT NULL DEFAULT('Pending'),
	refund_amount DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	customer_id VARCHAR(255) NOT NULL,
	order_number INT NOT NULL,
	
	#-- range constraint
	CONSTRAINT return_reason_chk CHECK(LENGTH(return_reason) <= 500),
	CONSTRAINT return_status_chk CHECK(return_status IN('Pending', 'Approved', 'Rejected')),
	CONSTRAINT refund_amount_chk CHECK(refund_amount >= 0.00),
	
	#-- key constraint
	CONSTRAINT return_pk PRIMARY KEY(tracking_number),
	CONSTRAINT return_fk1 FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
	CONSTRAINT return_fk2 FOREIGN KEY(order_number) REFERENCES order_t(order_number)
);

CREATE TABLE seller(
	seller_id VARCHAR(255) NOT NULL,
	seller_name VARCHAR(255) NOT NULL DEFAULT('Unknown Seller'),
	seller_rating FLOAT NOT NULL DEFAULT(0),
	
	#-- range constraint
	CONSTRAINT seller_id_chk CHECK(LENGTH(seller_id) <= 100),
	CONSTRAINT seller_rating_chk CHECK(seller_rating >= 0 AND seller_rating <= 5),
	
	#-- key constraint
	CONSTRAINT seller_pk PRIMARY KEY(seller_id),
	CONSTRAINT seller_fk1 FOREIGN KEY(seller_id) REFERENCES user_t(email_address)
);

CREATE TABLE product(
	serial_code INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	product_description TEXT,
	product_type CHAR DEFAULT('P'),
	picture BLOB,
	quantity INT NOT NULL DEFAULT(0),
	seller_id VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT price_chk CHECK(price >= 0.00),
	CONSTRAINT type_chk CHECK(product_type IN('P','D')), #-- 'P' = Physical Product, 'D' = Digital Product
	CONSTRAINT quantity_chk CHECK(quantity > 0),
	
	#-- key constraint
	CONSTRAINT product_pk PRIMARY KEY(serial_code),
	CONSTRAINT product_fk FOREIGN KEY(seller_id) REFERENCES seller(seller_id)
);

CREATE TABLE physical_product(
	physical_serial_code INT NOT NULL,
	product_size VARCHAR(10) DEFAULT('N/A'),
	size_multiplier INT DEFAULT(1),
	product_weight DECIMAL(65,2) DEFAULT(0.00),
	colour VARCHAR(50) DEFAULT('N/A'),
	
	#-- range constraint
	CONSTRAINT product_size_chk CHECK(product_size IN('N/A' ,'S', 'M', 'L')),
	CONSTRAINT product_size_multiplier_chk CHECK(size_multiplier >= 1),
	CONSTRAINT product_weight_chk CHECK(product_weight >= 0.01),
	
	#-- key constraint
	CONSTRAINT physical_pk PRIMARY KEY(physical_serial_code)
);

CREATE TABLE digital_product(
	digital_serial_code INT NOT NULL,
	file_type VARCHAR(100) NOT NULL DEFAULT('Text File (.txt)'),
	file_size DECIMAL(65,2) NOT NULL DEFAULT(0.1),
	
	#-- range constraint
	CONSTRAINT file_type_chk CHECK(file_type IN ('Text File (.txt)','Music File (.mp3)','Video File (.mp4)','PDF (.pdf)','Software Application (.exe)','Document (.docx)','Image File (.jpg)','Audio Book (.m4a)','Presentation (.pptx)','Spreadsheet (.xlsx)')),
	CONSTRAINT file_size_chk CHECK(file_size >= 0.1),
	
	#-- key constraint
	CONSTRAINT digital_pk PRIMARY KEY(digital_serial_code)
);

CREATE TABLE review(
	review_id INT NOT NULL AUTO_INCREMENT,
	reviewer_name VARCHAR(255) NOT NULL,
	rating INT NOT NULL,
	review_comment TEXT,
	date_made DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	number_of_likes INT NOT NULL DEFAULT(0),
	serial_code INT NOT NULL,
	
	#-- range constraint
	CONSTRAINT review_comment_chk CHECK(LENGTH(review_comment) >= 0 AND LENGTH(review_comment) <= 500),
	CONSTRAINT rating_chk CHECK(rating >= 1 AND rating <= 5),
	CONSTRAINT number_of_likes_chk CHECK(number_of_likes >= 0),
	
	#-- key constraint
	CONSTRAINT review_pk PRIMARY KEY(review_id),
	CONSTRAINT review_fk FOREIGN KEY(serial_code) REFERENCES product(serial_code)
);

CREATE TABLE cart_detail(
	cart_detail_id INT NOT NULL AUTO_INCREMENT,
	date_time_added DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	product_quantity INT NOT NULL DEFAULT(1),
	product_price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	cart_id INT NOT NULL,
	serial_code INT NOT NULL,

    #-- range constraint
    CONSTRAINT cart_quantity_chk CHECK(product_quantity >= 1),
    CONSTRAINT cart_price_chk CHECK(product_price >= 0.00),
    
	#-- key constraint
	CONSTRAINT cart_detail_pk PRIMARY KEY(cart_detail_id),
	CONSTRAINT cart_detail_fk1 FOREIGN KEY(cart_id) REFERENCES cart(cart_id),
	CONSTRAINT cart_detail_fk2 FOREIGN KEY(serial_code) REFERENCES product(serial_code)
);

CREATE TABLE wish_list_detail(
	wish_list_detail_id INT NOT NULL AUTO_INCREMENT,
	date_time_added DATE NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	wish_list_id INT NOT NULL,
	serial_code INT NOT NULL,
	
	#-- key constraint
	CONSTRAINT wish_list_detail_pk PRIMARY KEY(wish_list_detail_id),
	CONSTRAINT wish_list_detail_fk1 FOREIGN KEY(wish_list_id) REFERENCES wish_list(wish_list_id),
	CONSTRAINT wish_list_detail_fk2 FOREIGN KEY(serial_code) REFERENCES product(serial_code)
);

CREATE TABLE return_detail(
	return_detail_id INT NOT NULL AUTO_INCREMENT,
	product_price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	product_quantity INT NOT NULL DEFAULT(1),
	tracking_number INT NOT NULL,
	serial_code INT NOT NULL,
	
	#-- range constraint
	CONSTRAINT return_price_chk CHECK(product_price >= 0.00),
	CONSTRAINT return_quantity_chk CHECK(product_quantity >= 1),
	
	#-- key constraint
	CONSTRAINT return_detail_pk PRIMARY KEY(return_detail_id),
	CONSTRAINT return_detail_fk1 FOREIGN KEY(tracking_number) REFERENCES return_t(tracking_number),
	CONSTRAINT return_detail_fk2 FOREIGN KEY(serial_code) REFERENCES product(serial_code)
);

CREATE TABLE order_detail(
	order_detail_id INT NOT NULL AUTO_INCREMENT,
	product_price DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	product_quantity INT NOT NULL DEFAULT(1),
	order_number INT NOT NULL,
	serial_code INT NOT NULL,
	
	#-- range constraint
	CONSTRAINT order_price_chk CHECK(product_price >= 0.00),
	CONSTRAINT order_quantity_chk CHECK(product_quantity >= 1),
	
	#-- key constraint
	CONSTRAINT order_detail_pk PRIMARY KEY(order_detail_id),
	CONSTRAINT order_detail_fk1 FOREIGN KEY(order_number) REFERENCES order_t(order_number),
	CONSTRAINT order_detail_fk2 FOREIGN KEY(serial_code) REFERENCES product(serial_code)
);

CREATE TABLE payment(
	payment_id INT NOT NULL AUTO_INCREMENT,
	payment_type VARCHAR(4) NOT NULL DEFAULT('CARD'),
	payment_date_time DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	shipping_cost DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	order_total_cost DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	is_paid BOOLEAN DEFAULT(FALSE),
	order_number INT NOT NULL,
	
	#-- range constraint
	CONSTRAINT payment_type_chk CHECK(payment_type IN('CARD','COD')),
	CONSTRAINT shipping_cost_chk CHECK(shipping_cost >= 0.00),
	CONSTRAINT order_total_cost_chk CHECK(order_total_cost >=  0.00),
	
	#-- key constraint
	CONSTRAINT payment_pk PRIMARY KEY(payment_id),
	CONSTRAINT payment_fk FOREIGN KEY(order_number) REFERENCES order_t(order_number)
);

CREATE TABLE cash_on_delivery(
	cod_id INT NOT NULL,
    recipient_name VARCHAR(255) NOT NULL,

	#-- key constraint
	CONSTRAINT cod_pk PRIMARY KEY(cod_id),
	CONSTRAINT cod_fk FOREIGN KEY(cod_id) REFERENCES payment(payment_id)
);

CREATE TABLE card_t(
	card_id INT NOT NULL,
	card_number VARCHAR(60) NOT NULL,
    card_description VARCHAR(255),
    name_on_card VARCHAR(255) NOT NULL,
    card_expiry_date DATE NOT NULL DEFAULT(CURRENT_DATE),
    cvv_number VARCHAR(20) NOT NULL,

	#-- key constraint
	CONSTRAINT card_pk PRIMARY KEY(card_id),
	CONSTRAINT card_fk FOREIGN KEY(card_id) REFERENCES payment(payment_id)
);

CREATE TABLE shipper(
	shipper_id VARCHAR(255) NOT NULL,
	shipper_name VARCHAR(255) NOT NULL,
	
	#-- range constraint
	CONSTRAINT shipper_name_chk CHECK(LENGTH(shipper_name) <= 255),

	#-- key constraint
	CONSTRAINT shipper_pk PRIMARY KEY(shipper_id),
	CONSTRAINT shipper_fk FOREIGN KEY(shipper_id) REFERENCES user_t(email_address)
);

CREATE TABLE delivery(
	delivery_id INT NOT NULL AUTO_INCREMENT,
	delivery_cost DECIMAL(65,2) NOT NULL DEFAULT(0.00),
	delivery_type VARCHAR(20) NOT NULL DEFAULT('Warehouse'),
	shipper_id VARCHAR(255) NOT NULL,

	#-- range constraint
	CONSTRAINT delivery_cost_chk CHECK(delivery_cost >= 0.00),
	CONSTRAINT delivery_type_chk CHECK(delivery_type IN ('House', 'Warehouse', 'Seller', 'Return')),

	#-- key constraint
	CONSTRAINT delivery_pk PRIMARY KEY(delivery_id),
	CONSTRAINT delivery_fk FOREIGN KEY(shipper_id) REFERENCES shipper(shipper_id)
);

CREATE TABLE house(
	house_id INT NOT NULL,
	recipient_name VARCHAR(255) NOT NULL,
	is_signed BOOLEAN NOT NULL DEFAULT(FALSE),

	#-- key constraint
	CONSTRAINT house_pk PRIMARY KEY(house_id)
);

CREATE TABLE warehouse(
	warehouse_id INT NOT NULL,
	warehouse_name VARCHAR(255) NOT NULL,
	warehouse_availability  INT NOT NULL DEFAULT(0),
	warehouse_capacity INT NOT NULL DEFAULT(0),
	is_active BOOLEAN NOT NULL DEFAULT(TRUE),
	is_order_dropped_off BOOLEAN NOT NULL DEFAULT(FALSE),
	is_order_picked_up BOOLEAN NOT NULL DEFAULT(FALSE),

	#-- range constraint
	CONSTRAINT warehouse_availability_chk CHECK(warehouse_availability >= 0 AND warehouse_availability <= warehouse_capacity),
	CONSTRAINT warehouse_capacity_chk CHECK(warehouse_capacity >= 0 AND warehouse_capacity >= warehouse_availability),

	#-- key constraint
	CONSTRAINT warehouse_pk PRIMARY KEY(warehouse_id)
);

CREATE TABLE manager(
	manager_email_address VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT(TRUE),
    warehouse_id INT NOT NULL,

	#-- key constraint
	CONSTRAINT manager_pk PRIMARY KEY(manager_email_address),
	CONSTRAINT manager_fk FOREIGN KEY(warehouse_id) REFERENCES warehouse(warehouse_id)
);

CREATE TABLE delivery_detail(
	delivery_detail_id INT NOT NULL AUTO_INCREMENT,
	delivery_status VARCHAR(20) NOT NULL DEFAULT('Pending'),
	actual_delivery_date DATE NOT NULL DEFAULT(CURRENT_DATE),
	estimated_delivery_date DATE NOT NULL DEFAULT(CURRENT_DATE),
	delivery_id INT NOT NULL,
	order_number INT NOT NULL,

	#-- range constraint
	CONSTRAINT delivery_status_chk CHECK(delivery_status IN ('Pending', 'In Transit', 'Out For Delivery', 'Delivered', 'Returned')),

	#-- key constraint
	CONSTRAINT delivery_detail_pk PRIMARY KEY(delivery_detail_id),
	CONSTRAINT delivery_detail_fk1 FOREIGN KEY(delivery_id) REFERENCES delivery(delivery_id),
	CONSTRAINT delivery_detail_fk2 FOREIGN KEY(order_number) REFERENCES order_t(order_number)
);

CREATE TABLE administrator(
	administrator_id VARCHAR(255) NOT NULL,
	type_of_job VARCHAR(255) NOT NULL DEFAULT('Database'),

	#-- range constraint
	CONSTRAINT type_of_job_chk CHECK (type_of_job IN ('Database', 'Network', 'System', 'Manager')),

	#-- key constraint
	CONSTRAINT administrator_pk PRIMARY KEY(administrator_id),
	CONSTRAINT administrator_fk FOREIGN KEY(administrator_id) REFERENCES user_t(email_address)
);

CREATE TABLE log_book(
	log_book_id INT NOT NULL AUTO_INCREMENT,
	user_of_effect VARCHAR(255) NOT NULL DEFAULT('N/A'),
	date_time_of_change DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	type_of_change VARCHAR(255) NOT NULL DEFAULT('N/A'),
	reason_for_change TEXT NOT NULL,
	administrator_id VARCHAR(255) NOT NULL,

	#-- range constraint
	CONSTRAINT user_of_effect_chk CHECK(user_of_effect IN ('N/A', 'User', 'Customer', 'Shipper', 'Seller', 'Manager', 'Admininstrator')),
	CONSTRAINT type_of_change_chk CHECK(type_of_change IN ('N/A', 'Create', 'Update', 'Delete')),

	#-- key constraint
	CONSTRAINT log_book_pk PRIMARY KEY(log_book_id),
	CONSTRAINT log_book_fk FOREIGN KEY(administrator_id) REFERENCES administrator(administrator_id)
);

#-- display tables
DESCRIBE user_t;
DESCRIBE address_book;
DESCRIBE customer;
DESCRIBE cart;
DESCRIBE wish_list;
DESCRIBE order_t;
DESCRIBE return_t;
DESCRIBE seller;
DESCRIBE product;
DESCRIBE physical_product;
DESCRIBE digital_product;
DESCRIBE review;
DESCRIBE cart_detail;
DESCRIBE wish_list_detail;
DESCRIBE return_detail;
DESCRIBE order_detail;
DESCRIBE payment;
DESCRIBE cash_on_delivery;
DESCRIBE card_t;
DESCRIBE shipper;
DESCRIBE delivery;
DESCRIBE house;
DESCRIBE warehouse;
DESCRIBE manager;
DESCRIBE delivery_detail;
DESCRIBE administrator;
DESCRIBE log_book;