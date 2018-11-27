# TP5
## Formula
Commencer par changer votre projet qui contient `Formula`
pour passer du patron de conception *template method* à *strategy* comme décrit sur la feuille de TP.

## ShapeContainer
Implémentez les classes de l'exercice 2 du TD, en faisant évoluer les formes ajoutées dans le `ShapeContainer` de la classe `App` à chaque fois que vous finissez
une classe pour tester ce qu'ajoute cette classe.
Dans le fichier `App` vous supprimerez

~~~java
        graphicsContext.setFill(Color.AQUAMARINE);
        graphicsContext.fillOval(10,10,10,10);
~~~

Vous devriez comprendre comment faire des formes qui contiennent de la couleur.