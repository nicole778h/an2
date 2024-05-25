USE bysh
GO

GO
CREATE OR ALTER FUNCTION validareMachiaje
(@ingredient INT, @culoare INT, @pret INT)
RETURNS VARCHAR(100)
AS 
BEGIN
	DECLARE @mesaj VARCHAR(100)
	SET @mesaj = ''

	IF(NOT(EXISTS(SELECT cod FROM Ingrediente WHERE cod = @ingredient)))
		SET @mesaj += 'Nu exista machiaje cu acest id!'

	IF(NOT(EXISTS(SELECT cod FROM Culori WHERE cod = @culoare)))
		SET @mesaj += 'Nu exista culori cu acest id!'

	IF(@pret < 0)
		SET @mesaj += 'Pretul este incorect!'

	RETURN @mesaj
END


-- Function to validate Comanda
CREATE OR ALTER FUNCTION validareComanda
(@client INT, @data_livrarii DATETIME)
RETURNS VARCHAR(100)
AS 
BEGIN
	DECLARE @mesaj VARCHAR(100);
	SET @mesaj = '';

	IF(NOT(EXISTS(SELECT id FROM Clienti WHERE id = @client)))
		SET @mesaj += 'Nu exista client cu acest id!';

	IF(@data_livrarii < GETDATE())
		SET @mesaj += 'Data invalida!';

	RETURN @mesaj;
END;


-----SISTEMUL DE LOGARE-----
CREATE TABLE IstoricLogare
(
	id  INT PRIMARY KEY IDENTITY(1,1),
	actiune VARCHAR(15),
	tabel VARCHAR(15),
	data_executiei DATETIME
)


----- PROCEDURA CE INSEREAZA DATE IN TABELELE Machiaje SI Comunezi SI FACE ROLLBOCK PE INTREAGA PROCEDURA-----
GO
CREATE OR ALTER PROCEDURE AddMachiajeComenzi @ingredient INT, @culoare INT, @pret INT, @client INT, @data_livrarii DATETIME
AS
BEGIN
	BEGIN TRAN
	BEGIN TRY

		DECLARE @mesaj VARCHAR(100) = '';

		DECLARE @mesaj_machiaje VARCHAR(100) = dbo.validareMachiaje(@ingredient,@culoare,@pret);
		IF (@mesaj_machiaje <> '')
			SET @mesaj += @mesaj_machiaje + CHAR(13) + CHAR(10);

		DECLARE @mesaj_comanda VARCHAR(100) = dbo.validareComanda(@client,@data_livrarii);
		IF (@mesaj_comanda <> '')
			SET @mesaj += @mesaj_comanda + CHAR(13) + CHAR(10);

		IF (@mesaj <> '')
		BEGIN
			RAISERROR(@mesaj, 14, 1);
		END


		DECLARE @idMachiaje INT
		DECLARE @idComanda INT

		INSERT INTO Machiaje(ingredient,culoare,pret) VALUES(@ingredient,@culoare,@pret)
		SET @idMachiaje = SCOPE_IDENTITY()
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'Machiaje', CURRENT_TIMESTAMP)
		
		INSERT INTO Comenzi(client,data_livrarii) VALUES(@client,@data_livrarii)
		SET @idComanda = SCOPE_IDENTITY()
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'Comenzi', CURRENT_TIMESTAMP)

		INSERT INTO MachiajeComenzi(machiaj,comanda) VALUES (@idMachiaje,@idComanda)
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'MachiajeComenzi', CURRENT_TIMESTAMP)

		COMMIT TRAN
		SELECT 'Transaction committed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked'
	END CATCH
END

SELECT * FROM Machiaje
SELECT * FROM Comenzi
SELECT * FROM MachiajeComenzi
SELECT * FROM Ingrediente
SELECT * FROM Culori
SELECT * FROM Clienti
SELECT * FROM IstoricLogare

--- Scenariul de succes
EXEC AddMachiajeComenzi 1, 1, 100, 1, '2023-05-05 12:00:00'

--- Rollback
EXEC AddMachiajeComenzi 100, 1, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 100, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 1, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 1, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 1, -100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 1, 100, 100, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi 1, 1, 100, 1, '2020-05-05 12:00:00'




----- PROCEDURA CE INSEREAZA DATE IN TABELELE Machiaje SI Comunezi SI PASTREAZA CE E CORECT-----
GO
CREATE OR ALTER PROCEDURE AddMachiajeComenzi2 @ingredient INT, @culoare INT, @pret INT, @client INT, @data_livrarii DATETIME
AS
BEGIN
	DECLARE @inserareMachiaje INT
	SET @inserareMachiaje = 0

	BEGIN TRAN
	BEGIN TRY
		DECLARE @mesaj_machiaje VARCHAR(100) = dbo.validareMachiaje(@ingredient,@culoare,@pret);
		IF (@mesaj_machiaje <> '')
		BEGIN
			RAISERROR(@mesaj_machiaje, 14, 1);
		END
		DECLARE @idMachiaje INT

		INSERT INTO Machiaje(ingredient,culoare,pret) VALUES(@ingredient,@culoare,@pret)
		SET @idMachiaje = SCOPE_IDENTITY()
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'Machiaje', CURRENT_TIMESTAMP)
	
		COMMIT TRAN
		SELECT 'Transaction Machiaje committed'
		SET @inserareMachiaje = @idMachiaje
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction Machiaje rollbacked'
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('ROLLBACK', 'Machiaje', CURRENT_TIMESTAMP)
	END CATCH



	DECLARE @inserareComanda INT
	SET @inserareComanda = 0

	BEGIN TRAN
	BEGIN TRY
		DECLARE @mesaj_comanda VARCHAR(100) = dbo.validareComanda(@client,@data_livrarii);
		IF (@mesaj_comanda <> '')
		BEGIN
			RAISERROR(@mesaj_comanda, 14, 1);
		END
		DECLARE @idComanda INT
		
		INSERT INTO Comenzi(client,data_livrarii) VALUES(@client,@data_livrarii)
		SET @idComanda = SCOPE_IDENTITY()
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'Comenzi', CURRENT_TIMESTAMP)
	
		COMMIT TRAN
		SELECT 'Transaction Comanda committed'
		SET @inserareComanda = @idComanda
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction Comanda rollbacked'
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('ROLLBACK', 'Comanda', CURRENT_TIMESTAMP)
	END CATCH


	BEGIN TRAN
	BEGIN TRY
		IF(@inserareComanda = 0 OR @inserareMachiaje = 0)
		BEGIN
			RAISERROR(@mesaj_comanda, 14, 1);
		END
		
		INSERT INTO MachiajeComenzi(machiaj,comanda) VALUES (@inserareMachiaje,@inserareComanda)
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('Insert', 'MachiajeComenzi', CURRENT_TIMESTAMP)
	
		COMMIT TRAN
		SELECT 'Transaction MachiajeComanda committed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction MachiajeComanda rollbacked'
		INSERT INTO IstoricLogare(actiune, tabel, data_executiei) VALUES ('ROLLBACK', 'MachiajeComanda', CURRENT_TIMESTAMP)
	END CATCH

END



SELECT * FROM Machiaje
SELECT * FROM Comenzi
SELECT * FROM MachiajeComenzi
SELECT * FROM Ingrediente
SELECT * FROM Culori
SELECT * FROM Clienti
SELECT * FROM IstoricLogare

--- Scenariul de succes
EXEC AddMachiajeComenzi2 1, 1, 100, 1, '2023-05-05 12:00:00'

--- Rollback
--- Se adauga Comanda, dar Machiaje si Machiaje Comenzi nu se adauga
EXEC AddMachiajeComenzi2 100, 1, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi2 1, 100, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi2 1, 1, 100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi2 1, 1,  100, 1, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi2 1, 1, -100, 1, '2023-05-05 12:00:00'

--- Se adauga Machiaje, dar Comanda si MachiajeComenzi nu se adauga
EXEC AddMachiajeComenzi2 1, 1, 100, 100, '2023-05-05 12:00:00'
EXEC AddMachiajeComenzi2 1, 1, 100, 1, '2020-05-05 12:00:00'