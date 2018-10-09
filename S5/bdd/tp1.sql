-- Loïc Escales

alter session set nls_date_format = 'dd/mm/yyyy hh24:mi';

-- Exercice 1

SELECT NumVol
FROM VOLS
WHERE VilleD = 'Paris';

-- Exercice 2

SELECT DISTINCT P.NumPil
FROM PILOTES P, VOLS V
WHERE P.NumPil = V.NumPil
AND NOT(VilleD = 'Paris' AND VilleA = 'Marseille');

-- Exercice 3

SELECT NumVol
FROM VOLS
WHERE 24 * (DateA - DateD) BETWEEN 2 AND 4;

-- Exercice 4

SELECT NumAv, NomAv
FROM AVIONS
WHERE NomAv NOT LIKE '%a%'; 

-- Exercice 5

SELECT NomPil, (EXTRACT(YEAR FROM CURRENT_DATE) - NaisPil) AS "AgePil"
FROM PILOTES
WHERE VillePil = 'Nice'
AND (EXTRACT(YEAR FROM CURRENT_DATE) - NaisPil) > 35;

-- Exercice 6

SELECT NumVol
FROM VOLS
WHERE NumAv IS NULL OR NumAv NOT IN ('101', '401');

-- Exercice 7

SELECT NumPil, NVL(VillePil, 'Ville inconnue')
FROM PILOTES;

-- Exercice 8

SELECT NumVol, VilleD, DateD
FROM VOLS
WHERE VilleA = 'Marseille'
ORDER BY TRUNC(DateD), TO_DATE(TO_CHAR(DateD, 'HH:MI:SS'), 'HH:MI:SS') DESC;

-- Exercice 9

SELECT NVL(SUBSTR(NomAv, 0, INSTR(NomAv, ' ')), NomAv)
FROM AVIONS;

-- Exercice 10

SELECT COUNT(*)
FROM PILOTES
WHERE VillePil = 'Nice';

-- Exercice 11

SELECT AVG(EXTRACT(YEAR FROM CURRENT_DATE) - NaisPil)
FROM PILOTES
WHERE NAISPIL IS NOT NULL;

-- Exercice 12

SELECT COUNT(DISTINCT VILLEA)
FROM VOLS;

-- Exercice 13

SELECT MIN(CAPAV), COUNT(*) - COUNT(capav) AS "Nb avions cap null"
FROM AVIONS;

-- Exercice 14

SELECT SUM(CASE NumVol WHEN 'V101' THEN NbPlaces ELSE 0 END) AS "V101",
       SUM(CASE NumVol WHEN 'V222' THEN NbPlaces ELSE 0 END) AS "V222"
FROM RESERVATIONS;

-- Exercice 15

SELECT DISTINCT VilleA
FROM VOLS
WHERE VilleA IN (SELECT DISTINCT VilleD FROM VOLS);

-- Exercice 16

SELECT NomCl, CLASSE
FROM CLIENTS C
JOIN RESERVATIONS R ON C.NumCl = R.NumCl
WHERE R.NumVol = 'V790';

-- Exercice 17

SELECT NomCl
FROM CLIENTS C1
JOIN (SELECT C.NumCl, COUNT(*) AS "R"
      FROM CLIENTS C
      JOIN RESERVATIONS R ON C.NumCl = R.NumCl
      GROUP BY C.NumCl
) C2 ON C1.NumCl = C2.NumCl
WHERE C2.R > 3;

-- Exercice 18

SELECT NumPil
FROM (SELECT P.NumPil, 24 * SUM(DateA - DateD) AS "HEURES"
      FROM PILOTES P
      JOIN VOLS V ON P.NumPil = V.NumPil
      WHERE TO_CHAR(DateD, 'MM') = '12'
      GROUP BY P.NumPil
) P
WHERE P.HEURES > 170;

-- Exercice 19

-- la classe touriste est au même prix que la classe éco
SELECT R.NUMCL, SUM(V.COUTVOL * NVL(C.COEFFPRIX, 1))
FROM RESERVATIONS R
JOIN VOLS V ON R.NumVol = V.NumVol
JOIN DEFCLASSES C ON R.NumVol = C.NumVol AND R.Classe = C.Classe
GROUP BY R.NUMCL;

-- Exercice 20
-- Exercice 21
-- Exercice 22
-- Exercice 23
-- Exercice 24
-- Exercice 25
-- Exercice 26
-- Exercice 27
-- Exercice 28
-- Exercice 29
-- Exercice 30
-- Exercice 31
-- Exercice 32
-- Exercice 34
-- Exercice 35


