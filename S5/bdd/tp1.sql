-- Exercice 1

SELECT NumVol
FROM VOLS
WHERE VilleD = 'Paris';

-- Exercice 2

SELECT NumPil
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

SELECT NomPil, (EXTRACT(YEAR FROM CURRENT_TIME) - NaisPil) AS "AgePil"
FROM PILOTES
WHERE VillePil = 'Nice'
AND (EXTRACT(YEAR FROM CURRENT_TIME) - NaisPil) > 35;

-- Exercice 6

-- doit afficher NUL
SELECT NumVol
FROM VOLS
WHERE NumVol NOT IN ('V101', 'V401');

-- Exercice 7

SELECT NumPil, NVL(VillePil, 'Ville inconnue')
FROM PILOTES;

-- Exercice 8

SELECT NumVol, VilleD, DateD
FROM VOLS
WHERE VilleA = 'Marseille'
ORDER BY TRUNC(DateD), EXTRACT(HOUR FROM DateD) DESC;

-- Exercice 9

SELECT  NomAv
FROM AVIONS;

-- Exercice 10
-- Exercice 11
-- Exercice 12
-- Exercice 13
-- Exercice 14
-- Exercice 15
-- Exercice 16
-- Exercice 17
-- Exercice 18
-- Exercice 19
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


