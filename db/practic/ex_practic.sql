CREATE DATABASE Netflix
GO
USE Netflix


CREATE TABLE TipSeriale
( 
   id_tip INT PRIMARY KEY IDENTITY,
   denumire VARCHAR(30),
   descriere VARCHAR(30)
  
);

CREATE TABLE Canale
(   
   id_canal INT PRIMARY KEY IDENTITY,
   denumire VARCHAR(30),
   rating INT
);

CREATE TABLE Seriale
(
   id_serial INT PRIMARY KEY IDENTITY,
   denumire VARCHAR(30),
   prezentare VARCHAR(30),
   nr_sezoane INT,
   id_tip INT,
   id_canal INT,
   CONSTRAINT fk_TipSerial FOREIGN KEY(id_tip) REFERENCES TipSeriale(id_tip),
   CONSTRAINT fk_CanalSerial FOREIGN KEY(id_canal) REFERENCES Canale(id_canal)
);

CREATE TABLE Utilizatori
(
   id_utilizator INT PRIMARY KEY IDENTITY,
   nume VARCHAR(30),
   prenume VARCHAR(30),
   gen VARCHAR(30),
   varsta INT
);

CREATE TABLE SerialeUrmate
(
   id_utilizator INT,
   id_serial INT,
   data_lansarii DATE,
   ora_difuzarii TIME,
   CONSTRAINT pk_SerialeUrmate PRIMARY KEY(id_utilizator,id_serial),
   CONSTRAINT fk_UtilizatorSerialeUrmate FOREIGN KEY(id_utilizator) REFERENCES Utilizatori(id_utilizator),
   CONSTRAINT fk_SerialSerialeUrmate FOREIGN KEY(id_serial) REFERENCES Seriale(id_serial)
);


GO
CREATE PROCEDURE AdaugaSerial
@id_serial INT,
@id_utilizator INT,
@data_lansarii DATE,
@ora_difuzarii TIME
AS
BEGIN
     IF EXISTS (SELECT 1 FROM SerialeUrmate WHERE id_serial = @id_serial AND id_utilizator = @id_utilizator)
	 BEGIN
	            UPDATE SerialeUrmate SET data_lansarii = @data_lansarii , ora_difuzarii = @ora_difuzarii WHERE id_serial = @id_serial AND id_utilizator = @id_utilizator;
	 END
	 ELSE
	 BEGIN
	            INSERT INTO SerialeUrmate( id_serial, id_utilizator, data_lansarii, ora_difuzarii)
				VALUES ( @id_serial, @id_utilizator, @data_lansarii, @ora_difuzarii);
	 END
END


-- Adăugarea datelor în tabelul TipuriSeriale
SET IDENTITY_INSERT TipSeriale ON;
INSERT INTO TipSeriale (id_tip, denumire, descriere)
VALUES
    (2, 'Comedie', 'Seriale comice'),
    (3, 'SF', 'Seriale științifico-fantastice');
SET IDENTITY_INSERT TipSeriale OFF;

-- Adăugarea datelor în tabelul Canale
SET IDENTITY_INSERT Canale ON;
INSERT INTO Canale (id_canal, denumire, rating)
VALUES
    (1, 'HBO', 4.5),
    (2, 'Netflix', 4.8),
    (3, 'Amazon Prime Video', 4.2);
SET IDENTITY_INSERT Canale OFF;

-- Adăugarea datelor în tabelul Seriale
SET IDENTITY_INSERT Seriale ON;
INSERT INTO Seriale (id_serial, denumire, prezentare, nr_sezoane, id_tip, id_canal)
VALUES
    (1, 'Breaking Bad', 'Chemistry teacher', 5, 1, 1),
    (2, 'Friends', 'A group of friends', 10, 2, 2),
    (3, 'Stranger Things', 'Kids encounter supernatural', 4, 3, 2);
SET IDENTITY_INSERT Seriale OFF;

-- Adăugarea datelor în tabelul Utilizatori
SET IDENTITY_INSERT Utilizatori ON;
INSERT INTO Utilizatori (id_utilizator, nume, prenume, gen, varsta)
VALUES
    (1, 'Smith', 'John', 'M', 30),
    (2, 'Doe', 'Jane', 'F', 25),
    (3, 'Johnson', 'Robert', 'M', 35);
SET IDENTITY_INSERT Utilizatori OFF;

SET IDENTITY_INSERT Utilizatori ON;
INSERT INTO Utilizatori (id_utilizator, nume, prenume, gen, varsta)
VALUES
    (4, 'Ana', 'Jane', 'M', 30),
    (5, 'Maria', 'Jane', 'F', 25);
 
SET IDENTITY_INSERT Utilizatori OFF;


-- Adăugarea datelor în tabelul SerialeUrmate

INSERT INTO SerialeUrmate (id_utilizator, id_serial, data_lansarii, ora_difuzarii)
VALUES
    (1, 1, '2023-01-09', '20:00:00'),
    (2, 2, '2023-01-10', '19:30:00'),
    (3, 3, '2023-01-11', '21:00:00');

INSERT INTO SerialeUrmate (id_utilizator, id_serial, data_lansarii, ora_difuzarii)
VALUES
     (4, 1, '2023-02-09', '21:00:00'),
	 (5, 1, '2023-02-10', '22:00:00');

CREATE VIEW SerialeCuCelPutin3Utilizatori AS
SELECT
    S.id_serial,
    S.denumire AS NumeSerial,
    COUNT(DISTINCT SU.id_utilizator) AS NumarUtilizatori
FROM
    SerialeUrmate SU
JOIN
    Seriale S ON SU.id_serial = S.id_serial
GROUP BY
    S.id_serial, S.denumire
HAVING
    COUNT(DISTINCT SU.id_utilizator) >= 3;


SELECT * FROM SerialeCuCelPutin3Utilizatori;


EXEC AdaugaSerial @id_serial = 1, @id_utilizator = 4, @data_lansarii = '2023-02-09', @ora_difuzarii = '21:00:00';


EXEC AdaugaSerial @id_serial = 2, @id_utilizator = 5, @data_lansarii = '2023-02-10', @ora_difuzarii = '22:00:00';