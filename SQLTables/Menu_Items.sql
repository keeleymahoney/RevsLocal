/*
Create the table with 
    Item_Name as primary key (which is a text value)
    Price of item which is a decimal
    Menu_Category which is a text value
*/


CREATE TABLE Menu_Items(
    Item_Name TEXT PRIMARY Key,
    Price DECIMAL,
    Menu_Category TEXT 
);

/*
Insert into the newly created table with 
    Item_Name as primary key (which is a text value)
    Price of item which is a decimal
    Menu_Category which is a text value
*/

INSERT INTO Menu_Items (item_name, price, menu_category)

VALUES

/*
    This is based on the Real menu at Rev's. Each menu item is able to be ordered in real life
    The categories for a menu are entree, dessert, side, and beverage
*/
('Bacon Cheeseburger', 8.29, 'Entree'),
('Classic Hamburger', 6.89, 'Entree'),
('Gig Em Patty Melt', 7.59, 'Entree'),
('Cheeseburger', 6.89, 'Entree'),
('Black Bean Burger', 7.59, 'Entree'),
('Revs Grilled Chicken Sandwich', 8.39, 'Entree'),
('Spicy Chicken Sandwich', 8.39, 'Entree'),
('Aggie Chicken Club', 8.39, 'Entree'),
('2 Corn Dogs', 4.99, 'Entree'),
('2 Hot Dogs', 4.99, 'Entree'),
('3 Chicken Tenders', 4.99, 'Entree'),

/*
Separate into two separate menu items instead of combining because cost is different. 2 chicken bacon
ranch wraps does not equal the cost of two 1 chicken bacon ranch wraps
*/
('2 Chicken Bacon Ranch Wraps', 6.00, 'Entree'),
('1 Chicken Bacon Ranch Wrap', 3.49, 'Entree'),

/*
Separate into two separate menu items instead of combining because cost is different. 2 classic chicken
 wraps does not equal the cost of two 1 classic chicken wraps
*/
('2 Classic Chicken Wraps', 5.00, 'Entree'),
('1 Classic Chicken Wrap', 2.99, 'Entree'),
('Fish Sandwich', 7.99, 'Entree'),
('Tuna Melt', 7.99, 'Entree'),
('Chicken Caesar Salad', 8.29, 'Entree'),
('French Fries', 1.99, 'Side'),

/*Separate into type of shake for easy access*/
('Chocolate Aggie Shake', 4.49, 'Dessert'),
('Strawberry Aggie Shake', 4.49, 'Dessert'),
('Vanilla Aggie Shake', 4.49, 'Dessert'),
('Oreo Cookie Aggie Shake', 4.49, 'Dessert'),

/*Separate into type of shake and type of cookie for easy access*/
('Sugar Cookie Chocolate Ice Cream Sundae', 4.69, 'Dessert'),
('Sugar Cookie Strawberry Ice Cream Sundae', 4.69, 'Dessert'),
('Sugar Cookie Vanilla Ice Cream Sundae', 4.69, 'Dessert'),
('Sugar Cookie Oreo Cookie Ice Cream Sundae', 4.69, 'Dessert'),

('Chocolate Chip Cookie Chocolate Ice Cream Sundae', 4.69, 'Dessert'),
('Chocolate Chip Cookie Strawberry Ice Cream Sundae', 4.69, 'Dessert'),
('Chocolate Chip Cookie Vanilla Ice Cream Sundae', 4.69, 'Dessert'),
('Chocolate Chip Cookie Oreo Cookie Ice Cream Sundae', 4.69, 'Dessert'),

/*Separate into type of ice cream for easy access*/
('Chocolate Double Scoop Ice Cream', 3.29, 'Dessert'),
('Strawberry Double Scoop Ice Cream', 3.29, 'Dessert'),
('Vanilla Double Scoop Ice Cream', 3.29, 'Dessert'),
('Oreo Cookie Double Scoop Ice Cream', 3.29, 'Dessert'),


('Root Beer Float', 5.49, 'Dessert'),
('16 OZ Aquafina Water', 1.79, 'Beverage'),
('20 OZ Aquafina Water', 2.19, 'Beverage'),
('20 OZ Fountain Drink', 1.99, 'Beverage'),

('Surf N Turf Burger', 15.00 , 'Seasonal');


/*Display the table*/
SELECT * FROM menu_items;

