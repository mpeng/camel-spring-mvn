select * from tutorials

update tutorials set published = true where id = 1

CREATE TYPE point AS (
x float,
y float
);

CREATE TABLE coordiantes (
	id serial PRIMARY KEY,
	location point
);

INSERT INTO coordiantes (location) VALUES
(point(1.0, 2.0)),
(point(3.5, 4.5));

select * from coordiantes;

SELECT * FROM coordinates;

SELECT * FROM coordiantes;


CREATE TABLE EMPLOYEES (id int, department_id int, boss_id int, name varchar(100), salary int);

INSERT  INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (1, 2, 6, 'Doris Leonard', 130931);
INSERT  INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (1, 2, 6, 'Doris Leonard', 130931);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (2, 1, 11, 'Jennifer Fleming', 128629);
INSERT    INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (3, 1, 2, 'Tanya Cockburn', 109413);
INSERT    INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (4, 1, 2, 'Samuel Haley', 127829);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (5, 3, 7, 'Kellie Barret', 58648);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (6, 2, 11, 'Kathryn Atteberry', 135433);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (7, 3, 11, 'Victor Lewin', 144994);
INSERT  INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (8, 2, 1, 'Hal Post', 66565);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (9, 2, 1, 'Gale Robinson', 54062);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (10, 2, 1, 'Josiah Durand', 91250);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (11, 4, 11, 'Wally Triggs', 250000);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (12, 1, 2, 'Cordell Wilton', 108452);
INSERT    INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (13, 3, 7, 'Dion Humphrey', 45999);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (14, 3, 7, 'Finn Keyes', 71073);
INSERT   INTO EMPLOYEES (id, department_id, boss_id, name, salary)
         VALUES (15, 2, 1, 'Lauren Derby', 130873);
		 
		 select * from EMPLOYEES;


CREATE TABLE DEPARTMENTS (id int, name varchar(50));

INSERT  INTO DEPARTMENTS (id, name) VALUES (1, 'Sales');
INSERT  INTO DEPARTMENTS (id, name) VALUES (2, 'Engineering');
INSERT  INTO DEPARTMENTS (id, name) VALUES (3, 'Accounting');
INSERT  INTO DEPARTMENTS (id, name) VALUES (4, 'Executive');

		 select * from DEPARTMENTS;

select e.name, d.name from EMPLOYEES e, DEPARTMENTS d where e.department_id = d.id;

-- Return a list of employees who have bosses that are in a different department than their own

select e.name from EMPLOYEES e 
where e.department_id not in ( select b.department_id from EMPLOYEES b where e.boss_id = b.id )

select distinct e.name from EMPLOYEES e, DEPARTMENTS d 
where e.boss_id in ( select a.id from  EMPLOYEES a where a.department_id <> e.department_id )

-- Select employees who have the biggest salary in their departments

select e.name from EMPLOYEES e where e.salary in ( select Max( e2.salary ) from EMPLOYEES e2 group by department_id )

select e.name, d.name from EMPLOYEES e, DEPARTMENTS d 
where e.department_id = d.id and e.salary in ( select Max( e2.salary ) from EMPLOYEES e2 group by department_id )

select e.name, e.salary, d.name from EMPLOYEES e, DEPARTMENTS d where e.department_id = d.id and e.salary in
 ( select max(a.salary) from EMPLOYEES a, DEPARTMENTS d where a.department_id = d.id group by d.name )

-- Find the total salary in each department

select sum(e.salary) from EMPLOYEES e group by e.department_id

select sum(salary), d.name from EMPLOYEES e, DEPARTMENTS d where e.department_id = d.id group by d.name

-- Retrieve the top 5 employees who have the highest salaries

select distinct e.name, e.salary from EMPLOYEES e order by e.salary desc limit 5

-- Retrieve the list of employees along with name of ther boss.

select distinct e.name, b.name from EMPLOYEES e, EMPLOYEES b where e.boss_id = b.id and e.name != b.name

-- select employee who salary is above average in his/her department

select distinct e.name from EMPLOYEES e 
where e.salary > ( select avg( e2.salary ) from EMPLOYEES e2 where e.department_id = e2.department_id group by department_id );


select distinct a.name, b.name  from EMPLOYEES a, DEPARTMENTS b  where a.department_id = b.id and a.salary >
(select avg( e.salary ) from EMPLOYEES e, DEPARTMENTS d  where e.department_id = d.id and b.id = d.id group by d.name ) order by a.name;





