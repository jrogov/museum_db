CREATE KEYSPACE IF NOT EXISTS museum
WITH replication = { 'class' : 'NetworkTopologyStrategy', 'cluster1' : 3, 'cluster2' : 3, 'cluster3' : 3 };

USE museum;

CREATE TABLE relocation(
    id text PRIMARY KEY,
    exhibitid text,
    fromroomid text,
    toroomid text
);

CREATE TABLE visit(
    id text PRIMARY KEY,
    visitorid text,
    date text,
    tickettype text,
    price double,
    excursionid text
);

