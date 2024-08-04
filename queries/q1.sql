SELECT
    EXTRACT(WEEK FROM order_date) AS week_number,
    COUNT(order_number) AS total_orders_per_week
FROM
    Customer_Orders
GROUP BY
    EXTRACT(WEEK FROM order_date)
ORDER BY
    Week_number;
