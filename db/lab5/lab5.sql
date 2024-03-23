
------------------------------------------- MachiajeComenzi --------------------------------------------------------------

-- Validarea datelor din tabelul MachiajeComenzi (many-to-many)
CREATE FUNCTION validareMachiajeComenzi(@machiaje INT, @comanda INT, @descriere VARCHAR(20))
RETURNS VARCHAR(200)
AS 
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''
	IF (NOT(EXISTS(SELECT id FROM Machiaje WHERE id = @machiaje)))
		SET @errorMessage = @errorMessage + 'Machiajele cu id-ul ' + CONVERT(VARCHAR, @machiaje) + ' nu exista. '

	IF (NOT(EXISTS(SELECT id FROM Comenzi WHERE id = @comanda)))
		SET @errorMessage = @errorMessage + 'Comanda cu id-ul ' + CONVERT(VARCHAR, @comanda) + ' nu exista. '

	IF (@descriere = '')
		SET @errorMessage = @errorMessage + 'Descrierea nu poate fi nula.'

	IF (EXISTS(SELECT machiaj, comanda FROM MachiajeComenzi WHERE machiaj = @machiaje AND comanda = @comanda))
		SET @errorMessage = @errorMessage + 'Valorile deja exista in tabel.'
	RETURN @errorMessage
END
--PRINT dbo.validareMachiajeComenzi(1,2);


GO
--Adaugare nota in tabelul MachiajeComenzi
CREATE  OR ALTER PROCEDURE adaugaMachiajeComenzi(@machiaje INT, @comanda INT, @descriere VARCHAR(20))
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiajeComenzi(@machiaje,@comanda, @descriere)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage
		RETURN
	END

	
GO
--Read OchelariComenzi FROM MachiajeComenzi (get_all)
CREATE  OR ALTER PROCEDURE readMachiajeComenzi(@machiaje INT, @comanda INT)
AS
BEGIN
	-- READ
	SELECT * FROM MachiajeComenzi WHERE machiaj = @machiaje AND comanda = @comanda
END

GO

--Update descriere FROM MachiajeComenzi
CREATE  OR ALTER PROCEDURE updateNota(@machiaje INT, @comanda INT, @descriere VARCHAR(20))
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiajeComenzi(@machiaje,@comanda,@descriere)

	IF (@errorMessage = 'Valorile deja exista in tabel.')
	BEGIN
		-- UPDATE
		UPDATE MachiajeComenzi SET descriere = @descriere WHERE machiaj = @machiaje and comanda = @comanda
	END
	ELSE 
	BEGIN
		PRINT 'Nu se poate modifica deoarece nu exista.'
	END

END

GO

--Delete MachiajeComenzi FROM MachiajeComenzi
CREATE  OR ALTER PROCEDURE deleteMachiajeComenzi(@machiaje INT, @comanda INT, @descriere VARCHAR(20))
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiajeComenzi(@machiaje, @comanda, @descriere)

	IF (@errorMessage = 'Valorile deja exista in tabel.')
	BEGIN
		-- DELETE
		DELETE FROM MachiajeComenzi WHERE machiaj = @machiaje AND comanda = @comanda
	END
	ELSE
	BEGIN
		PRINT 'Nu s-a gasit in tabel.'
	END
END

------------------------------------------- Ochelari --------------------------------------------------------------

-- Validarea datelor din tabelul Ochelari
CREATE FUNCTION validareMachiaje(@ingredient INT, @culoare INT, @pret INT)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''
	
	IF (NOT(EXISTS(SELECT cod FROM Ingrediente WHERE cod = @ingredient)))
		SET @errorMessage = @errorMessage + 'Ingredientul cu codul ' + CONVERT(VARCHAR, @ingredient) + ' nu exista. '

	IF (NOT(EXISTS(SELECT cod FROM Culori WHERE cod = @culoare)))
		SET @errorMessage = @errorMessage + 'Culoarea cu codul ' + CONVERT(VARCHAR, @culoare) + ' nu exista. '

	if (@pret < 0)
		SET @errorMessage = @errorMessage + 'Pretul nu poate fi un numar negativ. '

		RETURN @errorMessage
END


GO
--Adaugare in tabelul Machiaje
CREATE  OR ALTER PROCEDURE adaugaMachiaje(@ingredient INT, @culoare INT, @pret INT)
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiaje(@ingredient,@culoare ,@pret)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage
		RETURN
	END

	-- CREATE
	INSERT INTO Machiaje(ingredient,culoare,pret) VALUES (@ingredient,@culoare,@pret)
END

GO
--Read FROM Machiaje
CREATE  OR ALTER PROCEDURE readMachiaje
(@ingredient INT, @culoare INT)
AS
BEGIN
	-- READ
	SELECT * FROM Machiaje WHERE ingredient = @ingredient AND culoare = @culoare
END

GO
--Update Machiaje
CREATE  OR ALTER PROCEDURE updateMachiaje(@ingredient INT, @culoare INT, @pret INT)
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiaje(@ingredient,@culoare,@pret)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage;
	END
	ELSE 
	BEGIN
		IF (EXISTS(SELECT ingredient, culoare FROM Machiaje WHERE ingredient = @ingredient AND culoare = @culoare))
			UPDATE Machiaje SET pret = @pret WHERE ingredient = @ingredient AND culoare = @culoare
		ELSE
			PRINT 'Machiajele nu exista'
	END

END

GO

--Delete Machiaje
CREATE  OR ALTER PROCEDURE deleteMachiaje(@ingredient INT, @culoare INT, @pret INT)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareMachiaje(@ingredient,@culoare,@pret)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage;
	END
	ELSE 
	BEGIN
		IF (EXISTS(SELECT ingredient, culoare FROM Machiaje WHERE ingredient = @ingredient AND culoare = @culoare))
			DELETE FROM Machiaje WHERE ingredient = @ingredient AND culoare = @culoare
		ELSE 
			PRINT 'Machiajele nu exista.'
	END
END



------------------------------------------- Comenzi --------------------------------------------------------------
-- Validarea datelor din tabelul Comenzi
CREATE FUNCTION validareComenzi(@client INT, @data_livrarii DATE)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''

	IF (NOT(EXISTS(SELECT id FROM Clienti WHERE id = @client)))
		SET @errorMessage = @errorMessage + 'Clientul cu id-ul ' + CONVERT(VARCHAR, @client) + ' nu exista. '
	RETURN @errorMessage
END


GO
--Adaugare in tabelul Comenzi
CREATE  OR ALTER PROCEDURE adaugaComenzi(@client INT, @data_livrarii DATE)
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareComenzi(@client,@data_livrarii)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage
		RETURN
	END

	-- CREATE
	INSERT INTO Comenzi(client,data_livrarii) VALUES (@client,@data_livrarii)
END

GO
--Read FROM Comenzi
CREATE  OR ALTER PROCEDURE readComenzi(@client INT, @data_livrarii DATE)
AS
BEGIN
	-- READ
	SELECT * FROM Comenzi WHERE client = @client
END

GO

--Update Comenzi
CREATE  OR ALTER PROCEDURE updateComenzi(@client INT, @data_livrarii DATE)
AS
BEGIN

	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareComenzi(@client, @data_livrarii)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage;
	END
	ELSE 
	BEGIN
		IF (EXISTS(SELECT client FROM Comenzi WHERE client = @client))
			UPDATE Comenzi SET data_livrarii = @data_livrarii WHERE client = @client 
		ELSE
			PRINT 'Comanda nu exista.'
	END

END

GO

--Delete Comenzi
CREATE  OR ALTER PROCEDURE deleteComenzi(@client INT, @data_livrarii DATE)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = dbo.validareComenzi(@client, @data_livrarii)

	IF (@errorMessage != '')
	BEGIN
		PRINT 'ERROR: ' + @errorMessage;
	END
	ELSE 
	BEGIN
		IF (EXISTS(SELECT client FROM Comenzi WHERE client = @client))
			DELETE FROM Comenzi WHERE client = @client
		ELSE 
			PRINT 'Comanda nu exista.'
	END
END


------------------------------------------- VIEW URI SI INDECSI --------------------------------------------------------------

CREATE OR ALTER VIEW viewMachiaje
AS
	SELECT ingredient, culoare FROM Machiaje
GO

CREATE OR ALTER VIEW viewComenzi
AS
	SELECT data_livrarii, client FROM Comenzi
GO

SELECT * FROM viewMachiaje
SELECT * FROM viewComenzi

CREATE INDEX N_idx_Machiaje_info ON Machiaje(ingredient ASC, culoare ASC);
CREATE INDEX N_idx_Comenzi ON Comenzi(data_livrarii ASC, client ASC);

SELECT ingredient, culoare FROM Machiaje
SELECT data_livrarii,client FROM Comenzi

SELECT * FROM viewComenzi
SELECT * FROM viewMachiaje

SELECT * FROM Comenzi