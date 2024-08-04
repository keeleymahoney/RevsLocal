SELECT
    EXTRACT(HOUR FROM order_time) AS hour_number,
    COUNT(order_number) AS total_orders_per_hour
FROM
    Customer_Orders
GROUP BY
    EXTRACT(HOUR FROM order_time)
ORDER BY
    hour_number;
