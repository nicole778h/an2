
-- 1) Categoriile de angajati care au cel mult 2 persoane
SELECT R.rol, COUNT(*) AS nr_angajati
FROM Roluri R
RIGHT OUTER JOIN Angajati A
ON R.id = A.rol
GROUP BY R.rol HAVING COUNT(*) <= 2

-- 2) Denumirea producatorilor culoriilor care apartin machiajelor cu un pret mai mare sau egal cu 200
SELECT  P.denumire, C.cod, P.id
FROM Producatori P
LEFT OUTER JOIN Culori C
ON C.producator = P.id
LEFT OUTER JOIN Machiaje M
ON C.cod = M.culoare
WHERE M.pret >= 800

-- 3) Ce orase dintre Cluj-Napoca, Sibiu, Bucuresti au producatori ce au vandut cel putin 2 machiaje cu ingrediente cu pret < 500
SELECT P.oras , COUNT(*) AS nr_machiaje
FROM Producatori P
RIGHT OUTER JOIN Ingrediente I
ON I.producator = P.id
RIGHT OUTER JOIN Machiaje M
ON I.cod = M.ingredient
RIGHT OUTER JOIN MachiajeComenzi C
ON M.id = C.machiaj
WHERE M.pret<500 AND P.oras in ('Cluj-Napoca', 'Sibiu', 'Bucuresti') 
GROUP BY P.oras HAVING COUNT(*) >= 2

-- 4) Numele, prenumele si numarul programarilor medicilor care au avut pacienti de gen masculin in luna noiembrie sau decembrie a anului 2022
SELECT DISTINCT M.nume, M.prenume, COUNT(*) AS nr_servicii
FROM Angajati M
RIGHT OUTER JOIN Servicii P
ON M.id = P.angajat
RIGHT OUTER JOIN Clienti O
ON P.client = O.id
WHERE P.data >= '2022-11-01' AND P.data < '2023-01-01'
GROUP BY M.nume, M.prenume

--5) Medicii ai caror pacienti au comandat ochelari cu pret > 601
SELECT M.nume, M.prenume, COUNT(*) AS nr_clienti
FROM Angajati M
RIGHT OUTER JOIN Servicii P
ON M.id = P.angajat
LEFT OUTER JOIN Comenzi C
ON C.client = P.client
INNER JOIN MachiajeComenzi O
ON O.comanda = C.id
INNER JOIN Machiaje T
ON O.machiaje = T.id
WHERE T.pret > 601
GROUP BY M.nume, M.prenume

--6) Categoria de sex care a comandat mai multe perechi de ochelari cu rama de model 1
SELECT TOP 1 P.sex ,COUNT(*) AS nr_ochelari
FROM Pacienti P
RIGHT OUTER JOIN Comenzi C
ON P.id = C.pacient
RIGHT OUTER JOIN OchelariComenzi O 
ON O.comanda = C.id
LEFT OUTER JOIN Ochelari T 
ON O.ochelari = T.id
WHERE T.rama = 1
GROUP BY P.sex ORDER BY nr_ochelari DESC

-- 7) Medicii care nu sunt in concediu in perioada 01-01-2023 -> 01-06-2023
SELECT DISTINCT M.nume, M.prenume
FROM Angajati M
LEFT OUTER JOIN Concedii C 
ON M.id = C.angajat
WHERE M.rol = 1 AND ( (C.data_sfarsit < '2023-01-01') OR (C.data_inceput > '2023-06-01') OR (C.data_inceput IS NULL AND C.data_sfarsit IS NULL))

-- 8) Top 3 cele mai vandute branduri de lentile
SELECT TOP 3 P.denumire, COUNT(*) AS nr_ochelari
FROM Producatori P
RIGHT OUTER JOIN Lentile L 
ON L.producator = P.id
RIGHT OUTER JOIN Ochelari O 
ON O.lentile = L.cod
RIGHT OUTER JOIN OchelariComenzi C 
ON C.ochelari = O.id
GROUP BY P.denumire
ORDER BY COUNT(*) DESC

-- 9) Nr de pacienti de sex feminin ai cabinetului oftalmologic
SELECT DISTINCT COUNT(*) AS nr_pacienti
FROM Angajati M
RIGHT OUTER JOIN Programari P
ON M.id = P.medic
LEFT OUTER JOIN Pacienti O
ON P.pacient = O.id
WHERE O.sex = 'F'

--10) Top 3 cei mai vanduti ochelari pacientilor de sex masculin
SELECT TOP 3 O.id, COUNT(*) AS nr_vanzari
FROM Ochelari O
RIGHT OUTER JOIN OchelariComenzi OC
ON OC.ochelari = O.id
RIGHT OUTER JOIN Comenzi C
ON C.id = OC.comanda
LEFT OUTER JOIN Pacienti P
ON C.pacient = P.id
WHERE P.sex = 'M'
GROUP BY O.id ORDER BY COUNT(*) DESC


-- 5 CU WHERE: 2), 3), 4), 5), 6)
-- 3 CU GROUP BY: 1), 3), 4)
-- 2 CU DISTINCT: 4), 7)
-- 2 CU HAVING: 1), 3)
-- 7 CU INF DIN MAI MULT DE 2 TABELE: 2), 3), 4), 5), 6), 7), 8)
-- 2 CU TABELE IN RELATIA M-N
