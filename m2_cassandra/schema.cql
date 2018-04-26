CREATE KEYSPACE museum
WITH replication = { 'class': 'SimpleStrategy', 'replication_factor' : 1};

USE museum;

CREATE TABLE moving(
   move_id int PRIMARY KEY,
   exhibit text,
   pre_hall text,
   post_hall text,
   );

CREATE TABLE visiting(
   visit_id int PRIMARY KEY,
   visiter text,
   date timestamp,
   type text,
   price int,
   excursion text,
   );


// INSERT INTO moving (move_id, exhibit, pre_hall, post_hall) VALUES(?,?,?,?);
// INSERT INTO visiting (visit_id, visiter, date, type, price, excursion) VALUES(?,?,?,?,?,?);
