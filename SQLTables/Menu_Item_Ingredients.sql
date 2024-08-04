
-- Created Menu_Item_Ingredients table, a junction table between Menu_Items and Ingredients
CREATE TABLE Menu_Item_Ingredients (
    -- Primary Key are row numbers that's automatically appended (Starts at 1)
    ID SERIAL PRIMARY KEY,
    Menu_Item_Name TEXT,
    Ingredient_Name TEXT,
    Quantity INT,
    -- The two attributes below are linked to Menu_Items and Ingredients data table
    FOREIGN KEY (Menu_Item_Name) REFERENCES Menu_Items(Item_Name),
    FOREIGN KEY (Ingredient_Name) REFERENCES ingredients(Ingredient_Name)
);

-- Adding Entries: each ingredient for menu items as separate entries
INSERT INTO menu_item_ingredients(Menu_Item_Name, Ingredient_Name, Quantity)
-- Follows the format (Menu Item, Ingredient, Quantity)

VALUES
('Bacon Cheeseburger', 'Burger Buns', 1),
('Bacon Cheeseburger', 'Burger Patties', 1),
('Bacon Cheeseburger', 'Bacon', 1),
('Bacon Cheeseburger', 'Cheese - American', 1),
('Bacon Cheeseburger', 'Napkins', 2),
('Bacon Cheeseburger', 'Bowls - Entrees', 1),



('Classic Hamburger', 'Burger Buns', 1),
('Classic Hamburger', 'Burger Patties', 1),
('Classic Hamburger', 'Cheese - American', 1),
('Classic Hamburger', 'Lettuce', 1),
('Classic Hamburger', 'Tomatoes', 1),
('Classic Hamburger', 'Onions', 1),
('Classic Hamburger', 'Pickles', 1),
('Classic Hamburger', 'Napkins', 2),
('Classic Hamburger', 'Bowls - Entrees', 1),

('Gig Em Patty Melt', 'Sandwich Bread', 2),
('Gig Em Patty Melt', 'Burger Patties', 1),
('Gig Em Patty Melt', 'Cheese - Swiss', 1),
('Gig Em Patty Melt', 'Onions', 1),
('Gig Em Patty Melt', 'Gig Em Sauce', 1),
('Gig Em Patty Melt', 'Napkins', 2),
('Gig Em Patty Melt', 'Bowls - Entrees', 1),

('Cheeseburger', 'Burger Patties', 1),
('Cheeseburger', 'Burger Buns', 1),
('Cheeseburger', 'Cheese - American', 1),
('Cheeseburger', 'Pickles', 1),
('Cheeseburger', 'Napkins', 2),
('Cheeseburger', 'Bowls - Entrees', 1),

('Black Bean Burger', 'Pickles', 1),
('Black Bean Burger', 'Black Bean Patties', 1),
('Black Bean Burger', 'Lettuce', 1),
('Black Bean Burger', 'Tomatoes', 1),
('Black Bean Burger', 'Onions', 1),
('Black Bean Burger', 'Napkins', 2),
('Black Bean Burger', 'Bowls - Entrees', 1),


('Revs Grilled Chicken Sandwich', 'Burger Buns', 1),
('Revs Grilled Chicken Sandwich', 'Chicken Patties - Grilled', 1),
('Revs Grilled Chicken Sandwich', 'Lettuce', 1),
('Revs Grilled Chicken Sandwich', 'Onions', 1),
('Revs Grilled Chicken Sandwich', 'Napkins', 2),
('Revs Grilled Chicken Sandwich', 'Bowls - Entrees', 1),

('Spicy Chicken Sandwich', 'Burger Buns', 1),
('Spicy Chicken Sandwich', 'Chicken Patties - Spicy Fried', 1),
('Spicy Chicken Sandwich', 'Lettuce', 1),
('Spicy Chicken Sandwich', 'Buffalo Sauce', 1),
('Spicy Chicken Sandwich', 'Ranch', 1),
('Spicy Chicken Sandwich', 'Napkins', 2),
('Spicy Chicken Sandwich', 'Bowls - Entrees', 1),

('Aggie Chicken Club', 'Burger Buns', 1),
('Aggie Chicken Club', 'Chicken Patties - Fried', 1),
('Aggie Chicken Club', 'Cheese - Swiss', 1),
('Aggie Chicken Club', 'Bacon', 1),
('Aggie Chicken Club', 'Avocado', 1),
('Aggie Chicken Club', 'Napkins', 2),
('Aggie Chicken Club', 'Bowls - Entrees', 1),

('2 Corn Dogs', 'Corn Dogs', 2),
('2 Corn Dogs', 'Ketchup Packets', 1),
('2 Corn Dogs', 'Mustard Packets', 1),
('2 Corn Dogs', 'Relish', 1),
('2 Corn Dogs', 'Onions', 1),
('2 Corn Dogs', 'Napkins', 2),
('2 Corn Dogs', 'Bowls - Entrees', 1),

('2 Hot Dogs', 'Hot Dogs', 2),
('2 Hot Dogs', 'Ketchup Packets', 1),
('2 Hot Dogs', 'Mustard Packets', 1),
('2 Hot Dogs', 'Relish', 1),
('2 Hot Dogs', 'Onions', 1),
('2 Hot Dogs', 'Napkins', 2),
('2 Hot Dogs', 'Bowls - Entrees', 1),

('3 Chicken Tenders', 'Chicken Tenders', 1),
('3 Chicken Tenders', 'Gig Em Sauce', 1),
('3 Chicken Tenders', 'Napkins', 2),
('3 Chicken Tenders', 'Bowls - Entrees', 1),
('3 Chicken Tenders', 'Forks', 1),

('2 Chicken Bacon Ranch Wraps', 'Chicken Tenders', 2),
('2 Chicken Bacon Ranch Wraps', 'Tortillas', 2),
('2 Chicken Bacon Ranch Wraps', 'Lettuce', 2),
('2 Chicken Bacon Ranch Wraps', 'Bacon', 2),
('2 Chicken Bacon Ranch Wraps', 'Avocado', 2),
('2 Chicken Bacon Ranch Wraps', 'Ranch', 2),
('2 Chicken Bacon Ranch Wraps', 'Bowls - Entrees', 1),
('2 Chicken Bacon Ranch Wraps', 'Napkins', 2),
('2 Chicken Bacon Ranch Wraps', 'Forks', 1),

('1 Chicken Bacon Ranch Wrap', 'Chicken Tenders', 1),
('1 Chicken Bacon Ranch Wrap', 'Tortillas', 1),
('1 Chicken Bacon Ranch Wrap', 'Lettuce', 1),
('1 Chicken Bacon Ranch Wrap', 'Bacon', 1),
('1 Chicken Bacon Ranch Wrap', 'Avocado', 1),
('1 Chicken Bacon Ranch Wrap', 'Ranch', 1),
('1 Chicken Bacon Ranch Wrap', 'Bowls - Entrees', 1),
('1 Chicken Bacon Ranch Wrap', 'Forks', 1),
('1 Chicken Bacon Ranch Wrap', 'Napkins', 2),

('2 Classic Chicken Wraps', 'Chicken Tenders', 2),
('2 Classic Chicken Wraps', 'Tortillas', 2),
('2 Classic Chicken Wraps', 'Lettuce', 2),
('2 Classic Chicken Wraps', 'Tomatoes', 2),
('2 Classic Chicken Wraps', 'Ranch', 2),
('2 Classic Chicken Wraps', 'Bowls - Entrees', 1),
('2 Classic Chicken Wraps', 'Forks', 1),
('2 Classic Chicken Wraps', 'Napkins', 2),

('1 Classic Chicken Wrap', 'Chicken Tenders', 1),
('1 Classic Chicken Wrap', 'Tortillas', 1),
('1 Classic Chicken Wrap', 'Lettuce', 1),
('1 Classic Chicken Wrap', 'Tomatoes', 1),
('1 Classic Chicken Wrap', 'Ranch', 1),
('1 Classic Chicken Wrap', 'Bowls - Entrees', 1),
('1 Classic Chicken Wrap', 'Forks', 1),
('1 Classic Chicken Wrap', 'Napkins', 2),

('Fish Sandwich', 'Fish Patties', 1),
('Fish Sandwich', 'Burger Buns', 1),
('Fish Sandwich', 'Tartar Sauce', 1),
('Fish Sandwich', 'Cheese - American', 1),
('Fish Sandwich', 'Bowls - Entrees', 1),
('Fish Sandwich', 'Napkins', 2),

('Tuna Melt', 'Tuna Salad', 1),
('Tuna Melt', 'Tomatoes', 1),
('Tuna Melt', 'Lettuce', 1),
('Tuna Melt', 'Cheese - American', 1),
('Tuna Melt', 'Sandwich Bread', 1),
('Tuna Melt', 'Bowls - Entrees', 1),
('Tuna Melt', 'Napkins', 2),

('Chicken Caesar Salad', 'Salad Bowls - Chicken Caesar', 1),
('Chicken Caesar Salad', 'Napkins', 2),
('Chicken Caesar Salad', 'Forks', 1),

('French Fries', 'Fries', 1),
('French Fries', 'Napkins', 2),
('French Fries', 'Bowls - Entrees', 1),

('Chocolate Aggie Shake', 'Ice Cream - Chocolate', 1),
('Chocolate Aggie Shake', 'Milk', 1),
('Chocolate Aggie Shake', 'Cups - Milkshake', 1),
('Chocolate Aggie Shake', 'Napkins', 2),
('Chocolate Aggie Shake', 'Straws', 2),

('Strawberry Aggie Shake', 'Ice Cream - Strawberry', 1),
('Strawberry Aggie Shake', 'Milk', 1),
('Strawberry Aggie Shake', 'Cups - Milkshake', 1),
('Strawberry Aggie Shake', 'Napkins', 2),
('Strawberry Aggie Shake', 'Straws', 2),

('20 OZ Fountain Drink', 'Cups - Drinks', 1),
('20 OZ Aquafina Water', 'Water Bottles - Aquafina 20-oz', 1),
('16 OZ Aquafina Water', 'Water Bottles - Aquafina 16-oz', 1),

('Root Beer Float', 'Root Beer', 1),
('Root Beer Float', 'Ice Cream - Vanilla', 1),
('Root Beer Float', 'Spoons', 1),
('Root Beer Float', 'Napkins', 2),
('Root Beer Float', 'Cups - Drinks', 1),

('Oreo Cookie Double Scoop Ice Cream', 'Ice Cream - Oreo Cookie', 2),
('Oreo Cookie Double Scoop Ice Cream', 'Bowls - Ice Cream', 1),
('Oreo Cookie Double Scoop Ice Cream', 'Spoons', 1),
('Oreo Cookie Double Scoop Ice Cream', 'Napkins', 2), 

('Vanilla Double Scoop Ice Cream', 'Ice Cream - Vanilla', 2),
('Vanilla Double Scoop Ice Cream', 'Bowls - Ice Cream', 1),
('Vanilla Double Scoop Ice Cream', 'Spoons', 1),
('Vanilla Double Scoop Ice Cream', 'Napkins', 2), 

('Strawberry Double Scoop Ice Cream', 'Ice Cream - Strawberry', 2),
('Strawberry Double Scoop Ice Cream', 'Bowls - Ice Cream', 1),
('Strawberry Double Scoop Ice Cream', 'Spoons', 1),
('Strawberry Double Scoop Ice Cream', 'Napkins', 2), 

('Chocolate Double Scoop Ice Cream', 'Ice Cream - Chocolate', 2),
('Chocolate Double Scoop Ice Cream', 'Bowls - Ice Cream', 1),
('Chocolate Double Scoop Ice Cream', 'Spoons', 1),
('Chocolate Double Scoop Ice Cream', 'Napkins', 2), 

('Chocolate Chip Cookie Oreo Cookie Ice Cream Sundae', 'Ice Cream - Oreo Cookie', 1),
('Chocolate Chip Cookie Oreo Cookie Ice Cream Sundae', 'Cookies - Chocolate Chip', 2),
('Chocolate Chip Cookie Oreo Cookie Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Chocolate Chip Cookie Oreo Cookie Ice Cream Sundae', 'Napkins', 2), 

('Chocolate Chip Cookie Vanilla Ice Cream Sundae', 'Ice Cream - Vanilla', 1),
('Chocolate Chip Cookie Vanilla Ice Cream Sundae', 'Cookies - Chocolate Chip', 2),
('Chocolate Chip Cookie Vanilla Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Chocolate Chip Cookie Vanilla Ice Cream Sundae', 'Napkins', 2), 

('Chocolate Chip Cookie Strawberry Ice Cream Sundae', 'Ice Cream - Strawberry', 1),
('Chocolate Chip Cookie Strawberry Ice Cream Sundae', 'Cookies - Chocolate Chip', 2),
('Chocolate Chip Cookie Strawberry Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Chocolate Chip Cookie Strawberry Ice Cream Sundae', 'Napkins', 2), 

('Chocolate Chip Cookie Chocolate Ice Cream Sundae', 'Ice Cream - Chocolate', 1),
('Chocolate Chip Cookie Chocolate Ice Cream Sundae', 'Cookies - Chocolate Chip', 2),
('Chocolate Chip Cookie Chocolate Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Chocolate Chip Cookie Chocolate Ice Cream Sundae', 'Napkins', 2), 

('Sugar Cookie Oreo Cookie Ice Cream Sundae', 'Ice Cream - Oreo Cookie', 1),
('Sugar Cookie Oreo Cookie Ice Cream Sundae', 'Cookies - Sugar', 2),
('Sugar Cookie Oreo Cookie Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Sugar Cookie Oreo Cookie Ice Cream Sundae', 'Napkins', 2), 

('Sugar Cookie Vanilla Ice Cream Sundae', 'Ice Cream - Vanilla', 1),
('Sugar Cookie Vanilla Ice Cream Sundae', 'Cookies - Sugar', 2),
('Sugar Cookie Vanilla Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Sugar Cookie Vanilla Ice Cream Sundae', 'Napkins', 2), 

('Sugar Cookie Strawberry Ice Cream Sundae', 'Ice Cream - Strawberry', 1),
('Sugar Cookie Strawberry Ice Cream Sundae', 'Cookies - Sugar', 2),
('Sugar Cookie Strawberry Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Sugar Cookie Strawberry Ice Cream Sundae', 'Napkins', 2), 

('Sugar Cookie Chocolate Ice Cream Sundae', 'Ice Cream - Chocolate', 1),
('Sugar Cookie Chocolate Ice Cream Sundae', 'Cookies - Sugar', 2),
('Sugar Cookie Chocolate Ice Cream Sundae', 'Bowls - Ice Cream', 1),
('Sugar Cookie Chocolate Ice Cream Sundae', 'Napkins', 2), 

('Oreo Cookie Aggie Shake', 'Ice Cream - Oreo Cookie', 1),
('Oreo Cookie Aggie Shake', 'Milk', 1),
('Oreo Cookie Aggie Shake', 'Cups - Milkshake', 1),
('Oreo Cookie Aggie Shake', 'Straws', 1),
('Oreo Cookie Aggie Shake', 'Napkins', 2),

('Vanilla Aggie Shake', 'Ice Cream - Vanilla', 1),
('Vanilla Aggie Shake', 'Milk', 1),
('Vanilla Aggie Shake', 'Cups - Milkshake', 1),
('Vanilla Aggie Shake', 'Straws', 1),
('Vanilla Aggie Shake', 'Napkins', 2),

('Surf N Turf Burger', 'Burger Buns', 1),
('Surf N Turf Burger', 'Burger Patties', 1),
('Surf N Turf Burger', 'Cheese - American', 1),
('Surf N Turf Burger', 'Fish Patties', 1),
('Surf N Turf Burger', 'Napkins', 2),
('Surf N Turf Burger', 'Napkins', 2),
('Surf N Turf Burger', 'Gig Em Sauce', 1);


-- Shows full database after created 
SELECT * FROM menu_item_ingredients;

