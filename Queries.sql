--CUSTOMER----------------------------------------------------------------------------------------------------------------------------

INSERT INTO customer 
VALUES (?, ?)

DELETE
 FROM customer 
 WHERE cid =  ?

SELECT * 
FROM customer

SELECT cid
FROM owns_pass O
WHERE cid =  ? AND 
	NOT EXISTS(
		SELECT V.vehicleNumber
					FROM vehicle V
					WHERE NOT EXISTS
					(SELECT A.vehicleNumber
					FROM access A
					WHERE V.vehicleNumber = A.vehicleNumber
					AND A.cid = O.cid))
					
SELECT * 
FROM customer 
WHERE cid = ?

SELECT name, C.cid, O.pid, balance 
FROM customer C, owns_pass O 
WHERE O.cid = C.cid and C.cid =?

UPDATE owns_pass SET balance = balance + ? 
WHERE cid = ?

UPDATE customer SET name = ? 
WHERE cid = ?

SELECT balance 
FROM owns_pass 
WHERE cid = ?

SELECT pid 
FROM owns_pass 
WHERE cid = ?

--Driveable----------------------------------------------------------------------------------------------------------------------------

INSERT INTO driveable 
VALUES (?, ?)

DELETE FROM vehicle 
WHERE vehicleNumber IN (
	SELECT vehicleNumber FROM driveable) 
	AND vehicleNumber = ?

SELECT * 
FROM driveable 
WHERE vehicleNumber = ?

SELECT D.vehicleNumber, D.type, V.age, V.capacity 
FROM driveable D INNER JOIN vehicle V ON D.vehicleNumber = V.vehicleNumber

--DrivenBy-----------------------------------------------------------------------------------------------------------------------------
INSERT INTO driven_by VALUES (?, ?, ?, ?)

DELETE FROM driveable 
WHERE vehicleNumber = ? AND
	from = ? AND 
	to = ?
	AND empId = ?
	
SELECT DB.empId, DV.name, DB.vehicleNumber, DA.type, DB.fromDate, DB.toDate
FROM driven_by DB
INNER JOIN driver DV
ON DB.empId = DV.empId 												  
INNER JOIN driveable DA  
ON DB.vehicleNumber = DA.vehicleNumber

--Driver-----------------------------------------------------------------------------------------------------------------------------

INSERT INTO driver 
VALUES (?, ?, ?, ?)


DELETE FROM driver 
WHERE empId = ?

SELECT * 
FROM driver

SELECT * 
FROM driver 
WHERE empId = ?

UPDATE driver SET address = ? 
WHERE empId = ?

UPDATE driver SET phoneNumber = ? 
WHERE empId =

SELECT * FROM driven_by 
WHERE empId = ?

SELECT * 
FROM driven_by 
WHERE empId = ? AND DATE_FORMAT(fromDate, '%Y-%m-%d') = ? 
ORDER BY fromDate

SELECT * 
FROM driver 
WHERE empID = ?

SELECT * 
FROM driven_by 
WHERE empId = ? AND WEEK(fromDate, 1) = WEEK(CURRENT_DATE)
ORDER BY fromDate

--Driverless-----------------------------------------------------------------------------------------------------------------------------
INSERT INTO driverless 
VALUES (?)

DELETE FROM vehicle 
WHERE vehicleNumber IN (
	SELECT vehicleNumber FROM driverless) 
	AND vehicleNumber = ?
	
SELECT * 
FROM driverless 
WHERE vehicleNumber = ?

SELECT D.vehicleNumber, V.age, V.capacity 
FROM driverless D 
INNER JOIN vehicle V ON D.vehicleNumber = V.vehicleNumber

--Has-----------------------------------------------------------------------------------------------------------------------------

INSERT INTO has
 VALUES (?, ?)
 
 DELETE FROM has 
 WHERE stopNumber = ? AND 
 routeNumber = ?
 
 SELECT * 
 FROM has
 
 --OwnsPass-----------------------------------------------------------------------------------------------------------------------------
 
 INSERT INTO owns_pass 
 VALUES (?, ?, ?)
 
 DELETE FROM owns_pass 
 WHERE pid = ? AND 
	cid = ?
	
SELECT C.cid, name, O.pid, balance 
FROM customer C NATURAL JOIN owns_pass O

 --Route-----------------------------------------------------------------------------------------------------------------------------
 
 INSERT INTO route 
 VALUES (?, ?, ?, ?)
 
 DELETE FROM route 
 WHERE routeNumber =?
 
 SELECT routeNumber, routeName, startTime, stopTime 
 FROM route
 
 SELECT s.stopNumber, stopName, location 
 FROM has h NATURAL JOIN stop s 
 WHERE h.routeNumber = ?
 
 SELECT * 
 FROM route 
 WHERE routeName LIKE '%?%'
 
SELECT routeNumber, COUNT(cid) AS 'Number of customers'
FROM follows f, access a
WHERE f.vehicleNumber = a.vehicleNumber 
GROUP BY routeNumber 
ORDER BY routeNumber


SELECT routeNumber, counts.n AS 'Number of customers'
FROM follows f, (SELECT vehicleNumber, count(cid) AS 'n' FROM access GROUP BY vehicleNumber) counts 
WHERE f.vehicleNumber = counts.vehicleNumber AND 
counts.n = (SELECT MAX(counts2.n2) 
				FROM 
					(SELECT routeNumber, count(cid) AS 'n2'
					FROM follows f2, access a2
					WHERE f2.vehicleNumber = a2.vehicleNumber GROUP BY routeNumber) counts2)
ORDER BY routeNumber


SELECT routeNumber, counts.n AS 'Number of customers' 
FROM follows f, (SELECT vehicleNumber, count(cid) AS 'n' FROM access GROUP BY vehicleNumber) counts 
WHERE f.vehicleNumber = counts.vehicleNumber AND 
counts.n = (SELECT MIN(counts2.n2) FROM 
	(SELECT routeNumber, count(cid) AS 'n2' 
	FROM follows f2, access a2 
	WHERE f2.vehicleNumber = a2.vehicleNumber GROUP BY routeNumber) counts2) 
ORDER BY routeNumber

  --Stop-----------------------------------------------------------------------------------------------------------------------------
  
  INSERT INTO stop 
  VALUES (?, ?, ?)
  
  DELETE FROM stop 
  WHERE stopNumber = ?
  
  SELECT * 
  FROM stop
  
  SELECT * 
  FROM stop 
  WHERE stopName LIKE '%?%'
  
  SELECT r.routeNumber, routeName 
  FROM has h NATURAL JOIN route r
  WHERE h.stopNumber = ?

   --Vehicle-----------------------------------------------------------------------------------------------------------------------------
   
INSERT INTO vehicle 
VALUES (?, ?, ?)

DELETE FROM vehicle 
WHERE vehicleNumber = ?

SELECT * 
FROM vehicle

SELECT * 
FROM vehicle 
WHERE vehicleNumber = ?

SELECT vehicleNumber AS 'Vehicle Number',  COUNT(vehicleNumber) AS 'Number of vehicles' 
FROM driven_by 
WHERE MONTH(fromDate) = ? 
GROUP BY vehicleNumber
