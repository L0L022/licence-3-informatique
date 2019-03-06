# 1.2.1

::1

# 1.2.3

Le client IPV4 arrive bien à communiquer avec le serveur IPV6 alors que ce sont deux protocoles différents.
Si ça marche c'est grâce à la double pile.

# 1.3.3

La connexion d'un client IPV6 à mon serveur ne pose pas de problèmes.
C'est parce que c'est un serveur IPV6.

# 1.4

fe80::38d0:5dcf:ee93:c124/64

# 2.1

La double pile IP permet à un hôte de pouvoir communiquer à la fois en IPV4 et en IPV6.
Il a donc deux adresses IP : une adresse IPV4 et une adresse IPV6.
Cela permet par exemple à un serveur d'être à la fois compatible avec les clients IPV4 et les clients IPV6 et cela de manière transparente.

# 2.2

Un client qui utilise la double pile ne décide pas directement qu'elle version du protocole il va utiliser.
Du point de vu de l'application ça reste de l'IPV6.
C'est le noyeau qui s'occupe du reste.
Si on lui donne une adresse IPV4 elle sera transformée en IPV6 (IPv4-mapped address).
Quand on se connecte via un nom de domaine on peut recevoir une (V4 ou V6) ou deux adresse IP. 

# 2.3

Pour écouter sur une seule adresse (bindAddr) : ServerSocket(int port, int backlog, InetAddress bindAddr)

java.net.preferIPv4Stack : l'application n'utilise que le protocole IPV4
java.net.preferIPv6Addresses : préfère l'utilisation des adresses IPV6, mais est toujours compatible IPV4

Pour que le serveur soit compatible uniquement avec l'IPV4 il suffit de passer ceci à Java : -Djava.net.preferIPv4Stack=true
En java on ne peut choisir qu'entre IPV4 ou double pile IPV6.
On ne peut pas utiliser seulement le protocole IPV6.
Voir : https://docs.oracle.com/javase/8/docs/technotes/guides/net/ipv6_guide/

On peut imaginer vouloir faire un serveur qui ne fait que IPV4 et un autre qui tourne en parallère qui ne fait que IPV6.

# 2.5

Je n'ai pas pu tester sur Windows mais il me semble qu'il désactive par défaut la double pile.
