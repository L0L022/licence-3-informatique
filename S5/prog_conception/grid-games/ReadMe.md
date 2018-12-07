# Grid Games

Vous avez un programme monolithique qui permet de jouer au morpion. Le but de ce TP est de rendre
ce programme modulaire d'une part et d'étendre ses fonctionnalités de manière à pouvoir jouer à d'autres jeux. 
L'approche habituelle d'architecture logiciel est le shéma Modèle Vue Controlleur (MVC).
Cette architecture consiste à séparer les focntionnalités en les 3 parties suivantes :

- Le *Modèle* : C'est le coeur méchanique de votre programmme. Dans le cadre de *Grid Games*
c'est l'endroit oùsont codées les fonctionnalités des règles du jeu et  de l'état du jeu.

- La *Vue* : Cette partie regroupe tout ce qui doit être affiché à l'utilisateur.

- Le *Controlleur* : Cette partie est l'interface avec l'extérieur qui va agir à la fois sur le modèle et sur la vue.
Le contrôlleur va gérer l'ensemble des actions de l'utilisateur comme les mouvement de souris et les entrées clavier et notifer
le modèle et la vue des actions qui les concernent.

Dans  le cadre de *JavaFX* le controlleur peut servir d'interface entre la vue et le modèle, ce qui corrèle fortement la vue et le controlleur.
 
La quasi-totalité des primitives nécessaires pour ce TP sont dans l'ébauche de morpion.
Je vous invite à lire le code et à me poser des questions si il y a des parties que vous ne 
comprenez pas.

Si vous n'avez pas de préférence pour les jeux à implémenter vous pouvez faire :
- Puissance 4
- Go 