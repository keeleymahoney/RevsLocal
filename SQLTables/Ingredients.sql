-- Created Ingredients table
CREATE TABLE Ingredients (
    ingredient_name text PRIMARY KEY,
    -- Quantity before: at the start of the week
    quantity_warning int,
    quantity int,
    -- Unit for quantity, individual or servings
    unit text,
    exp_date date,
    -- Storagen location: Pantry, Fridge, or Freezer
    storage_location text
);
-- Adding entries
INSERT INTO Ingredients (ingredient_name, quantity_warning, quantity, unit, exp_date, storage_location)

VALUES
('Burger Buns', 50, 310, 'Individual', '2024-03-02', 'Pantry'),
('Sandwich Bread', 50, 223, 'Individual', '2024-03-14', 'Pantry'),
('Burger Patties', 50, 530, 'Individual', '2024-03-24', 'Fridge'),
('Chicken Patties - Fried', 50, 843, 'Individual', '2024-03-24', 'Fridge'),
('Chicken Patties - Spicy Fried', 50, 279, 'Individual', '2024-03-24', 'Fridge'),
('Chicken Patties - Grilled', 50, 210, 'Individual', '2024-03-26', 'Fridge'),
('Chicken Tenders', 50, 102, 'Servings', '2024-03-16', 'Fridge'),
('Bacon', 50, 965, 'Servings', '2024-04-13', 'Fridge'),
('Black Bean Patties', 50, 602, 'Servings', '2024-03-16', 'Fridge'),
('Fish Patties', 50, 512, 'Servings', '2024-03-16', 'Fridge'),
('Hot Dogs', 50, 265, 'Individual', '2024-04-13', 'Fridge'),
('Hot Dog Buns', 50, 108, 'Individual', '2024-03-09', 'Pantry'),
('Tortillas', 50, 243, 'Individual', '2024-03-09', 'Pantry'),
('Corn Dogs', 50, 310, 'Individual', '2024-05-09', 'Freezer'),
('Fries', 50, 108, 'Servings', '2024-04-13', 'Freezer'),
('Cheese - American', 50, 958, 'Indivudal', '2024-03-30', 'Fridge'),
('Cheese - Pepper Jack', 50, 741, 'Individual', '2024-03-30', 'Fridge'),
('Cheese - Swiss', 50, 923, 'Individual', '2024-03-30', 'Fridge'),
('Lettuce', 50, 2281, 'Servings', '2024-03-20', 'Fridge'),
('Tomatoes', 50, 292, 'Servings', '2024-03-20', 'Fridge'),
('Onions', 50, 331, 'Servings', '2024-03-20', 'Fridge'),
('Pickles', 50, 530, 'Servings', '2024-03-20', 'Fridge'),
('Avocado', 50, 530, 'Servings', '2024-03-20', 'Fridge'),
('Ketchup Packets', 50, 912, 'Individual', '2025-01-01', 'Pantry'),
('Mustard Packets', 50, 912, 'Individual', '2025-01-01', 'Pantry'),
('Relish', 50, 912, 'Individual', '2025-01-01', 'Pantry'),
('Gig Em Sauce', 50, 402, 'Servings', '2025-01-01', 'Pantry'),
('Ranch', 50, 402, 'Servings', '2025-01-01', 'Pantry'),
('Buffalo Sauce', 50, 402, 'Servings', '2025-01-01', 'Pantry'),
('Tartar Sauce', 50, 102, 'Servings', '2025-01-01', 'Pantry'),
('Salad Bowls - Chicken Caesar', 50, 412, 'Individual', '2024-03-16', 'Pantry'),
('Tuna Salad', 50, 712, 'Servings', '2024-08-16', 'Fridge'),


('Cookies - Chocolate Chip', 50, 850, 'Count', '2024-05-21', 'Freezer'),
('Cookies - Sugar', 50, 850, 'Count', '2024-05-21', 'Freezer'),
('Ice Cream - Vanilla', 50, 652, 'Servings', '2024-05-11', 'Freezer'),
('Ice Cream - Strawberry', 50, 580, 'Servings', '2024-05-11', 'Freezer'),
('Ice Cream - Chocolate', 50, 720, 'Servings', '2024-05-11', 'Freezer'),
('Ice Cream - Oreo Cookie', 50, 733, 'Servings', '2024-05-11', 'Freezer'),
('Napkins', 50, 1310, 'Individual', '2040-01-01', 'Pantry'),
('Bowls - Entrees', 50, 142, 'Individual', '2040-01-01', 'Pantry'),
('Bowls - Ice Cream', 50, 1420, 'Individual', '2040-01-01', 'Pantry'),
('Forks', 50, 4051, 'Individual', '2040-01-01', 'Pantry'),
('Spoons', 50, 3051, 'Individual', '2040-01-01', 'Pantry'),
('Straws', 50, 3051, 'Individual', '2040-01-01', 'Pantry'),
('Cups - Drinks', 50, 1050, 'Individual', '2040-01-01', 'Pantry'),
('Cups - Milkshake', 50, 1030, 'Individual', '2040-01-01', 'Pantry'),

('Water Bottles - Aquafina 16-oz', 50, 1243, 'Individual', '2025-01-31', 'Pantry'),
('Water Bottles - Aquafina 20-oz', 50, 2123, 'Individual', '2025-01-31', 'Pantry'),
('Root Beer', 50, 2430, 'Individual', '2025-01-31', 'Pantry'),
('Milk', 50, 540, 'Individual', '2024-04-17', 'Pantry');

-- Displays all rows in Ingredients table
SELECT * FROM Ingredients;