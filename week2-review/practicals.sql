-- S E T U P --
CREATE TABLE employees(
	id		SERIAL PRIMARY KEY,
	first_name	VARCHAR(20),
	last_name	VARCHAR(20),
	salary		INTEGER,
	dept		VARCHAR(40)
);
INSERT INTO employees (first_name, last_name, salary, dept) VALUES 
	('Michael', 'Scott', 65, 'Sales'),
	('Dwight', 'Schrute', 75, 'Sales'),
	('Toby', 'Flanderson', 80, 'HR'),
	('Jim', 'Halpert', 90, 'Sales'),
	('Oscar', 'Martinez', 90, 'Accounting'),
	('Angela', 'Martin', 75, 'Accounting'),
	('Kevin', 'Malone', 70, 'Accounting'),
	('Holly', 'Flax', 60, 'HR'),
	('Creed', 'Branton', 70, 'Quality Assurance');

--Write a query to find all data in the table
SELECT * FROM employees;

--Write a query to find employees with a salary over 75
SELECT * FROM employees WHERE salary > 75;

--Write a query to find employees whose first name contains an 'e' or whose last name begins with 'S'
SELECT * FROM employees WHERE first_name SIMILAR TO '%(e|E)%' OR last_name SIMILAR TO 'S%';

--Write a query to find the first name of all employees who do not work in accounting
SELECT first_name FROM employees WHERE NOT dept = 'Accounting';

--Write a query to find the average salary of all employees whose last names begin with 'M'
SELECT AVG(salary) FROM employees WHERE last_name LIKE 'M%';

--Write a query to find the highest paid salesperson
SELECT * FROM employees WHERE dept = 'Sales' AND salary = (SELECT MAX(salary) FROM employees WHERE dept = 'Sales');

--Write a query to combine the resultsets of any two previous queries
SELECT * FROM employees WHERE salary > 75
UNION
SELECT * FROM employees WHERE NOT dept = 'Accounting';

--Remove all members of accounting from the database
DELETE FROM employees WHERE dept = 'Accounting';

-- S E T U P --
CREATE TABLE department(
	dept_id		SERIAL PRIMARY KEY,
	name		VARCHAR(40)
);
INSERT INTO department (name) VALUES ('Sales'), ('HR'), ('Accounting'), ('Customer Service');
INSERT INTO employees (first_name, last_name, salary, dept) VALUES
	('Oscar', 'Martinez', 90, 'Accounting'),
	('Angela', 'Martin', 75, 'Accounting'),
	('Kevin', 'Malone', 70, 'Accounting');
ALTER TABLE employees ADD COLUMN dept_id INTEGER REFERENCES department(dept_id);
UPDATE employees SET dept_id = 1 WHERE dept = 'Sales';
UPDATE employees SET dept_id = 2 WHERE dept = 'HR';
UPDATE employees SET dept_id = 3 WHERE dept = 'Accounting';
UPDATE employees SET dept_id = 4 WHERE dept = 'Quality Assurance';
ALTER TABLE employees DROP COLUMN dept;

--Write a query to find the salary of the lowest paid salesperson (HINT: use a join)
SELECT MIN(salary) FROM employees JOIN department ON (employees.dept_id = department.dept_id) WHERE name = 'Sales';

--Write a query to find the average salary of each department
SELECT department.name, AVG(salary) FROM employees JOIN department ON (employees.dept_id = department.dept_id) GROUP BY department.name;

--Write a query to find all possible combinations of employees and departments. How many records do you expect?
SELECT employees.first_name, employees.last_name, department.name FROM employees CROSS JOIN department;	
	--(number of employees) * (number of departments) = 9 * 4 = 36 records are expected

--Change the name of department 4 to 'Quality Assurance'
UPDATE department SET name = 'Quality Assurance' WHERE dept_id = 4;

--Remove both tables
DROP TABLE employees;	--Drop this table first to avoid a reference error
DROP TABLE department;