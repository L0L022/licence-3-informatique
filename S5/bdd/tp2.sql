-- Loïc Escales & Killian Wolfger

alter session set nls_date_format = 'dd/mm/yyyy hh24:mi:ss';

-- Question 1

DROP TABLE Reservations PURGE;
DROP TABLE Clients PURGE;
DROP TABLE Tarifs PURGE;
DROP TABLE Etapes PURGE;
DROP TABLE Trajets PURGE;
DROP TABLE Bus PURGE;
DROP TABLE Villes PURGE;

CREATE TABLE Villes (
	NomV VARCHAR(15) NOT NULL, 
	CONSTRAINT PK_Villes PRIMARY KEY(NomV)
);

CREATE TABLE Bus (
	NumB DECIMAL(5) NOT NULL,
	CapaciteB DECIMAL(2) NOT NULL,
	CONSTRAINT PK_Bus PRIMARY KEY(NumB)
);

CREATE TABLE Trajets (
	NumT DECIMAL(5) NOT NULL,
	NumB DECIMAL(5) NOT NULL,
	CONSTRAINT PK_Trajets PRIMARY KEY(NumT),
	CONSTRAINT FK_Trajets_NumB FOREIGN KEY(NumB) REFERENCES Bus(NumB)
);

CREATE TABLE Etapes (
	NumT DECIMAL(5) NOT NULL,
	NomV VARCHAR(15) NOT NULL,
	DateA DATE NOT NULL,
	DateD DATE NOT NULL,
	CONSTRAINT PK_Etapes PRIMARY  KEY(NumT, NomV),
	CONSTRAINT FK_Etapes_NumT FOREIGN KEY(NumT) REFERENCES Trajets(NumT),
	CONSTRAINT FK_Etapes_NomV FOREIGN KEY(NomV) REFERENCES Villes(NomV),
	CONSTRAINT Dom_DateAD CHECK(24*60*(DateD-DateA)>=5)
);

CREATE TABLE Tarifs (
	VilleD VARCHAR(15) NOT NULL,
	VilleA VARCHAR(15) NOT NULL,
	Prix DECIMAL(5,2) NOT NULL,
	CONSTRAINT PK_Tarifs PRIMARY KEY(VilleD, VilleA),
	CONSTRAINT FK_Tarifs_VilleD FOREIGN KEY(VilleD) REFERENCES Villes(NomV),
	CONSTRAINT FK_Tarifs_VilleA FOREIGN KEY(VilleA) REFERENCES Villes(NomV)
);

CREATE TABLE Clients (
	NumC DECIMAL(5) NOT NULL,
	NomC VARCHAR(30) NOT NULL,
	AdresseC VARCHAR(50) NOT NULL,
	CONSTRAINT PK_Clients PRIMARY KEY(NumC)
);

CREATE TABLE Reservations (
	NumR DECIMAL(5) NOT NULL,
	NumC DECIMAL(5) NOT NULL,
	NumT DECIMAL(5) NOT NULL,
	VilleD VARCHAR(15) NOT NULL,
	VilleA VARCHAR(15) NOT NULL,
	Nbplaces DECIMAL(2) NOT NULL CONSTRAINT Dom_Nbplaces CHECK(Nbplaces>0),
	CONSTRAINT PK_Reservation PRIMARY KEY(NumR),
	CONSTRAINT FK_Reservations_NumC FOREIGN KEY(NumC) REFERENCES Clients(NumC),
	CONSTRAINT FK_Reservations_NumT_VilleD FOREIGN KEY(NumT, VilleD) REFERENCES Etapes(NumT, NomV),
	CONSTRAINT FK_Reservations_NumT_VilleA FOREIGN KEY(NumT, VilleA) REFERENCES Etapes(NumT, NomV),
	CONSTRAINT FK_Reservations_VilleD_VilleA FOREIGN KEY(VilleD, VilleA) REFERENCES Tarifs(VilleD, VilleA),
	CONSTRAINT Dom_VilleAD CHECK(VilleA<>VilleD)
);	

-- Question 2

INSERT INTO Villes VALUES ('Marseille');
INSERT INTO Villes VALUES ('Arles');
INSERT INTO Villes VALUES ('Gap');
INSERT INTO Villes VALUES ('Abeilhan');

INSERT INTO Bus VALUES (1, 10);
INSERT INTO Bus VALUES (2, 20);
INSERT INTO Bus VALUES (3, 30);
INSERT INTO Bus VALUES (4, 40);
INSERT INTO Bus VALUES (5, 50);

INSERT INTO Trajets VALUES (01, 1);
INSERT INTO Trajets VALUES (02, 1);
INSERT INTO Trajets VALUES (03, 2);
INSERT INTO Trajets VALUES (04, 2);

INSERT INTO Etapes VALUES (1, 'Marseille', TO_DATE('16/09/2018 08:00:00'), TO_DATE('16/09/2018 08:05:00'));
INSERT INTO Etapes VALUES (2, 'Arles', TO_DATE('16/09/2018 09:30:00'), TO_DATE('16/09/2018 09:45:00'));
INSERT INTO Etapes VALUES (3, 'Gap', TO_DATE('17/09/2018 01:55:00'), TO_DATE('17/09/2018 03:00:01'));
INSERT INTO Etapes VALUES (4, 'Abeilhan', TO_DATE('18/09/2018 14:14:14'), TO_DATE('18/09/2018 14:20:59'));

INSERT INTO Tarifs VALUES ('Marseille', 'Abeilhan', 50.63);
INSERT INTO Tarifs VALUES ('Abeilhan', 'Marseille', 50.50);
INSERT INTO Tarifs VALUES ('Marseille', 'Arles', 10.10);
INSERT INTO Tarifs VALUES ('Marseille', 'Gap', 25.42);
INSERT INTO Tarifs VALUES ('Gap', 'Abeilhan', 200.99);

INSERT INTO Clients VALUES (0, 'Loïc, chaud comme la braise.', 'Près de chez toi...');
INSERT INTO Clients VALUES (1, 'Killian, Archiduc des crêpes', 'Poudlard');
INSERT INTO Clients VALUES (2, 'Laurent le void', 'dans le placard sous l escalier');
INSERT INTO Clients VALUES (3, 'Le joker de Valmante', 'derrière toi');
INSERT INTO Clients VALUES (4, 'Khaleesi', 'Ville de Qarth');
INSERT INTO Clients VALUES (5, 'Marie reine d Ecosse', 'au dessus de toi');

INSERT INTO Reservations VALUES (001, 0, 

-- Question 3
-- 1
-- 2
-- 3
-- 4
-- 5												
