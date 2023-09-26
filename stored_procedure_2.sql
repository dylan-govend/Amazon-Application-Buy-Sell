DELIMITER //

#-- This stored procedure removes an entire product from the customer's cart.
CREATE PROCEDURE remove_product_from_cart(
    IN p_customer_id VARCHAR(255),
    IN p_serial_code INT
)

BEGIN

    #-- variables for checking and processing
    DECLARE p_price DECIMAL(65,2);
    DECLARE p_quantity INT;
    DECLARE p_cart_id INT;
    DECLARE count_product INT;

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

    #-- count the occurrences of multiple batches of the same product in cart detail
    SELECT COUNT(*)
    INTO count_product
    FROM cart_detail
    WHERE cart_id = p_cart_id
    AND serial_code = p_serial_code;

    #-- if the product exists in the cart in multiple batches
    if (count_product > 1) THEN

        #-- p_quantity gets the batch quantity of the product from the cart detail
        SELECT SUM(product_quantity) 
        INTO p_quantity
        FROM cart_detail
        WHERE cart_id = p_cart_id
        AND serial_code = p_serial_code;

    ELSE

        #-- variable p_quantity gets the quantity of the product from the seller
        SELECT product_quantity
        INTO p_quantity
        FROM cart_detail
        WHERE serial_code = p_serial_code;

    END IF;

    IF (count_product >= 1) THEN

        #-- update the customer's cart total price and total number of products in cart
        UPDATE cart
        SET total_price = (total_price - (p_price * p_quantity)), total_product_num = (total_product_num - p_quantity)
        WHERE cart_id = p_cart_id;

        #-- display customer's cart
        SELECT *
        FROM cart
        WHERE cart_id = p_cart_id;

        #-- remove product's from cart
        DELETE FROM cart_detail
        WHERE cart_id = p_cart_id
        AND serial_code = p_serial_code;

        #-- display cart_detail
        SELECT *
        FROM cart_detail
        WHERE cart_id = p_cart_id;

    ELSE

        SELECT "Product was not found in cart." 
        AS error;

    END IF;

END //

DELIMITER ;