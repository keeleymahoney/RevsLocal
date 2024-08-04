-- Creating Employees Table
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY,
    manager BOOLEAN,
    employee_name TEXT,
    pswd TEXT
);

-- Appending Employees
INSERT INTO Employee (employee_id, manager, employee_name, pswd)
VALUES
-- ID, Manager (1=T|0=F), Name
(1, TRUE, 'Joanne Liu', '0000'), 
(2, TRUE, 'Brandon Cisneros', '0000'), 
(3, TRUE, 'Keeley Mahoney', '0000'), 
(4, TRUE, 'Vitoria Cicalese', '0000'), 
(5, TRUE, 'Alyan Tharani', '0000'), 
(6, FALSE, 'Aiden Cisneros', '0000'), 
(7, FALSE, 'Mia Zhang', '0000'), 
(8, FALSE, 'Lucas Martins', '0000'), 
(9, FALSE, 'Sofia Patel', '0000'), 
(10, FALSE, 'Ethan Watanabe', '0000'), 
(11, FALSE, 'Olivia Smith', '0000'), 
(12, FALSE, 'Noah Kim', '0000'), 
(13, FALSE, 'Isabella Johnson', '0000'), 
(14, FALSE, 'Liam Tan', '0000'), 
(15, FALSE, 'Emma Garcia', '0000');

SELECT * FROM Employee;

