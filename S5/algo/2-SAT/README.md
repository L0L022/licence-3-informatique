# 2-SAT

## Programme principal

Le programme qui lit un fichier de clauses est dans le fichier src/main/java/TwoSATMain.java

## Tests unitaires

Les tests unitaires permetant de tester tous les fichiers de clauses sont dans le fichier src/test/java/TwoSATTest.java

## Corriger StackOverflowError

Cette erreur survient lors du traitement d'un problème 2-SAT avec un très grand nombre de clauses (ex: 10 000).
Pour corriger l'erreur il faut lancer le programme en passant cet argument à java : -Xss5m
