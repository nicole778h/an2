Create database BeautyShop4
go
use BeautyShop4
go

Create table TipA
(tid INT PRIMARY KEY IDENTITY,
denumire varchar(50)
)

Create table Angajati
(aid INT PRIMARY KEY IDENTITY,
Name varchar(50),
salar INT,
tid INT FOREIGN KEY REFERENCES TipA(tid)
)

Create table Clienti
(cid INT PRIMARY KEY IDENTITY,
Name varchar(50),
aid INT FOREIGN KEY REFERENCES Angajati(aid)
)

Create table Tip
(tid INT PRIMARY KEY IDENTITY,
denumire varchar(50)
)

Create table Firme
(fid INT PRIMARY KEY IDENTITY,
Name varchar(50),
cod_caen varchar(50)
)

Create table Machiaje
( mid INT PRIMARY KEY IDENTITY,
nuanta INT,
Name varchar(50),
tid INT FOREIGN KEY REFERENCES Tip(tid)
)

Create table FirMach
(fmid INT PRIMARY KEY IDENTITY,
fir INT FOREIGN KEY REFERENCES Firme(fid),
mid INT FOREIGN KEY REFERENCES Machiaje(mid)
)

Create table Parfumuri
( pid INT PRIMARY KEY IDENTITY,
Name varchar(50),
fid INT FOREIGN KEY REFERENCES Firme(fid)
)

Create table Arome
(arid INT PRIMARY KEY IDENTITY,
Name varchar(50)
)

Create table ParfArom
(paid INT PRIMARY KEY IDENTITY,
pid INT FOREIGN KEY REFERENCES Parfumuri(pid),
arid INT FOREIGN KEY REFEREnCES Arome(arid)
)

Create table CliMach
(cmaid INT PRIMARY KEY IDENTITY,
cid INT FOREIGN KEY REFERENCES Clienti(cid),
mid INT FOREIGN KEY REFERENCES Machiaje(mid)
)
