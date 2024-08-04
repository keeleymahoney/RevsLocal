SELECT 
    COUNT(DISTINCT Customer_Orders.order_number) AS num_orders_with_fries
FROM 
    Customer_Orders
JOIN 
    Customer_Order_Details
ON 
    Customer_Orders.order_number = customer_order_details.order_number
WHERE
    Customer_Order_Details.menu_item = 'French Fries' and Customer_Orders.Order_Date = '2023-02-27';
