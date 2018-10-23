-- Loïc Escales & Killian Wolfger

set AUTOTRACE ON EXP

--1]
--SELECT IdP FROM Vente;

-----------------------------------------------------------------------------
--| Id  | Operation         | Name  | Rows  | Bytes | Cost (%CPU)| Time     |
-----------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |       |   999 | 12987 |     3   (0)| 00:00:01 |
--|   1 |  TABLE ACCESS FULL| VENTE |   999 | 12987 |     3   (0)| 00:00:01 |
-----------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--1 bis]
--SELECT IdP FROM Produit;

----------------------------------------------------------------------------------------
--| Id  | Operation            | Name          | Rows  | Bytes | Cost (%CPU)| Time     |
----------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT     |               |    13 |   169 |     2   (0)| 00:00:01 |
--|   1 |  INDEX FAST FULL SCAN| SYS_C00212917 |    13 |   169 |     2   (0)| 00:00:01 |
----------------------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--2]
--SELECT Quantite FROM Vente;

-----------------------------------------------------------------------------
--| Id  | Operation         | Name  | Rows  | Bytes | Cost (%CPU)| Time     |
-----------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |       |   999 | 12987 |     3   (0)| 00:00:01 |
--|   1 |  TABLE ACCESS FULL| VENTE |   999 | 12987 |     3   (0)| 00:00:01 |
-----------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--3]
--SELECT DISTINCT Quantite FROM Vente;

------------------------------------------------------------------------------
--| Id  | Operation          | Name  | Rows  | Bytes | Cost (%CPU)| Time     |
------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT   |       |   999 | 12987 |     4  (25)| 00:00:01 |
--|   1 |  HASH UNIQUE       |       |   999 | 12987 |     4  (25)| 00:00:01 |
--|   2 |   TABLE ACCESS FULL| VENTE |   999 | 12987 |     3   (0)| 00:00:01 |
------------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--4]
--SELECT Quantite FROM Vente WHERE Quantite = 8;

-----------------------------------------------------------------------------
--| Id  | Operation         | Name  | Rows  | Bytes | Cost (%CPU)| Time     |
-----------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |       |    54 |   702 |     3   (0)| 00:00:01 |
--|*  1 |  TABLE ACCESS FULL| VENTE |    54 |   702 |     3   (0)| 00:00:01 |
-----------------------------------------------------------------------------
--filter("QUANTITE"=8)
--dynamic statistics used: dynamic sampling (level=2)

--5]
--SELECT IdP FROM Vente WHERE IdP = 15;

-------------------------------------------------------------------------------
--| Id  | Operation        | Name     | Rows  | Bytes | Cost (%CPU)| Time     |
-------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT |          |    86 |  1118 |     2   (0)| 00:00:01 |
--|*  1 |  INDEX RANGE SCAN| PK_VENTE |    86 |  1118 |     2   (0)| 00:00:01 |
-------------------------------------------------------------------------------
--access("IDP"=15)
--dynamic statistics used: dynamic sampling (level=2)

--6]
SELECT COUNT( Quantite ) FROM Vente;

----------------------------------------------------------------------
--| Id  | Operation          | Name  | Rows  | Cost (%CPU)| Time     |
----------------------------------------------------------------------
--|   0 | SELECT STATEMENT   |       |     1 |     3   (0)| 00:00:01 |
--|   1 |  SORT AGGREGATE    |       |     1 |            |          |
--|   2 |   TABLE ACCESS FULL| VENTE |   999 |     3   (0)| 00:00:01 |
----------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--7]
--SELECT COUNT( Quantite ) FROM Vente GROUP BY Quantite;

------------------------------------------------------------------------------
--| Id  | Operation          | Name  | Rows  | Bytes | Cost (%CPU)| Time     |
------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT   |       |   999 | 12987 |     4  (25)| 00:00:01 |
--|   1 |  HASH GROUP BY     |       |   999 | 12987 |     4  (25)| 00:00:01 |
--|   2 |   TABLE ACCESS FULL| VENTE |   999 | 12987 |     3   (0)| 00:00:01 |
------------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--8]
--SELECT COUNT(Idp) FROM Produit;

---------------------------------------------------------------------------------
--| Id  | Operation             | Name          | Rows  | Cost (%CPU)| Time     |
---------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT      |               |     1 |     2   (0)| 00:00:01 |
--|   1 |  SORT AGGREGATE       |               |     1 |            |          |
--|   2 |   INDEX FAST FULL SCAN| SYS_C00212917 |    13 |     2   (0)| 00:00:01 |
---------------------------------------------------------------------------------
--dynamic statistics used: dynamic sampling (level=2)

--Explications et comparaisons, question 1 à 8 :

--Comparaison R1 et R2 : SELECT sur clef primaire / SELECT sur attribut de table
--  |--> Nous supposons que le coût d'execution (CE) sera le même pour les deux et le nombre d'élements obtenus sera le même
--  |--> Resultat : coût similaire et même nombre d'éléments rendu

--Comparaison R2 et R3 : SELECT sur attribut / SELECT sur attribut avec distinct
--  |--> Nous supposons que la R3 aura un CE plus élevé car elle procède a une supression des doublons, donc une opération suplémentaire
--  |--> Resultat : coût plus élevé pour R3 car opération suplémentaire

--Comparaison R2 et R4 : SELECT sur attribut / SELECT sur attribut avec condition(WHERE)
--  |--> Dans un premier abord nous supposons que le WHERE (R4) sera plus couteux (opération suplémentaire), cependant étant donné qu'il réduit le nombre de données à afficher,
--       peux être que ce temps d'opération est gagné en temps de stockage (moins de données a stocker qui seront renvoyées).
--  |--> Resultat : Les coûts sont indentiques cependant R4 necessite moins de Bytes pour stocker ses données

--Comparaison R4 et R5 : WHERE sur attribut / WHERE sur clef primaire
--  |--> Nous supposons avec force et determination que le WHERE sur la clef primaire (R5) sera moins couteux que la recherche sur un attribut quelconque du fait que les opérations
--       sur clef primaire ont été implémenté de sorte à être plus efficaces.
--  |--> Resultat : R5 à un coût plus faible cependant cette requête requiert plus de mémoire. Nous pouvons aussi noter que le nom de l'opération diffère quand le WHERE
--                  est effectué sur une clef primaire ("INDEX RANGE SCAN") ou sur un attribut ("TABLE ACCESS FULL").

--Comparaison R1 et R5 : SELECT sur clef primaire / SELECT sur clef primaire avec WHERE
--  |--> Nous supposons que le SELECT avec WHERE (R5) sera moins couteux car il renvoit un seul resultat et l'accès a ce dernier est direct.
--  |--> Resultat : la R5 est en effet moins couteuse, que ce soit en mémoire ou en cout d'execution.

--Comparaison R6 et R7 : COUNT sur attribut / COUNT sur attribuit avec GROUP BY
--  |--> Nous supponsons que la R7 aura un CE plus élevé car il y a une opération supplémentaire de classement après avoir obtenu le resultat de la requête.
--  |--> Resultat : Nous pouvons voir que la R7 est plus couteuse, cependant nous ne pouvons pas comparer les différence en question de mémoire car la colonne Bytes
--                  n'apparaît pas pour la R6. Qu'est-ce que cela signifie ?

--Comparaison R1 et R1 bis : SELECT sur clef primaire / SELECT sur même clef primaire d'une atre table
--  |--> On suppose que les resultats seront indentiques.
--  |--> Resultat : Les resultats sont drastiquement différents. L'opération effectué n'est en effet pas la même, R1("TABLE ACCESS FULL") / R1 bis("INDEX FAST FULL SCAN")

--9]
--CREATE OR REPLACE VIEW NbProduits AS SELECT COUNT(Idp) AS "Nombres de produits" FROM Produit;
--SELECT * FROM NbProduits;
