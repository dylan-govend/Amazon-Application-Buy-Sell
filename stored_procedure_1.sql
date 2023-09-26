DELIMITER //

#-- This stored procedure will add a user-defined quantity of a product to the customer's cart.
CREATE PROCEDURE add_product_to_cart(
    IN p_customer_id VARCHAR(255),
    IN p_serial_code INT,
    IN p_desired_quantity INT
)

BEGIN

    #-- variables for checking and processing
    DECLARE p_quantity INT;
    DECLARE p_cart_id INT;
    DECLARE p_price DECIMAL(65,2);

    #-- variable p_quantity gets the quantity of the product from the seller
    SELECT quantity
    INTO p_quantity
    FROM product
    WHERE serial_code = p_serial_code;

    #-- variable p_cart_id gets the customer's cart to add this product into
    SELECT cart_id 
    INTO p_cart_id
    FROM cart
    WHERE customer_id = p_customer_id;

    #-- variable p_price gets the current price of the product
    SELECT price
    INTO p_price
    FROM product
    WHERE serial_code = p_serial_code;

    #-- check if the product is already in the cart, recalulate p_desired_quantity
    SELECT (p_desired_quantity = p_desired_quantity + product_quantity)
    FROM cart_detail
    WHERE cart_id = p_cart_id
    AND serial_code = p_serial_code;

    #-- the customer's desired quantity is less than the seller's stock quantity
    IF (p_quantity >= p_desired_quantity) THEN

        #-- add the product to the cart
        INSERT INTO cart_detail(date_time_added, product_quantity, product_price, cart_id, serial_code)
        VALUES (NOW(), p_desired_quantity, p_price, p_cart_id, p_serial_code);

        #-- display changes made in the cart_detail
        SELECT *
        FROM cart_detail
        WHERE cart_id = p_cart_id;

        #-- update the cart with new total price and new total quantity of products
        UPDATE cart
        SET total_product_num = (total_product_num + p_desired_quantity), total_price = (total_price + (p_price * p_desired_quantity))
        WHERE cart_id = p_cart_id;

        #-- display changes made in the cart
        SELECT *
        FROM cart
        WHERE cart_id = p_cart_id;

        #-- display message of success
        SELECT 'Product successfully added to cart.' 
        AS message;

    ELSE

        #-- display error, since the customer's desired quantity exceeds the seller's stock quantity
        SELECT 'Product cannot be added to cart. Seller has insufficient stock.' 
        AS error;

        #-- suggest the correct quantity of products the customer can add to the cart
        SELECT 'Please reduce your quantity of the desired product to: ' 
        AS suggestion, p_quantity 
        AS seller_stock;

    END IF;

END //

DELIMITER ;