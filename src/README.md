# Readme

Nous utilisons [*docker*](https://docs.docker.com/get-docker/) et [*docker-compose*](https://docs.docker.com/compose/install/) pour le déployement.  

### Utilisateur

Pour lancer notre application, s'il s'agit d'une release, il suffit de lancer `docker-compose up`, et de se rendre sur [localhost:8080](http://localhost:8080). Sinon, il va falloir passer par l'étape de **Build**.

### Build

Pour pouvoir build notre projet, il faut avoir installé *maven* et *npm*.

La procédure d'installation (testé sous ubuntu 20.04):
```bash
sudo apt install maven npm;
```

Pour la première fois, il faut installer les dépendances pour le *front*. Il faut pour cela aller dans le dossier `src/front` et lancer la commande `npm install`. Il suffit alors de lancer `scripts/run-prod.sh` pour lancer notre application, accessible sur [localhost:8080](http://localhost:8080).

Pour faire à la Main : pour build le *front*, il faut lancer dans `src/front` la commande `ng build --prod`, et pour la *back*,  il faut lancer dans `src/back` la commande `mvn war:war`.


Pour lancer les tests unitaires, il faut lancer le script `scripts/run-test.sh`.

Pour ne lancer que le *back* et la *db*, afin de tester le *front* avec `ng serve --watch`: `docker-compose up --build --force-recreate --renew-anon-volumes back db`. Il faut ajouter `app` dans la liste des services pour ajouter *phpmyadmin*, accessible sur le port `4242`.

Pour lancer les tests avec *cypress*, il faut lancer : `$(npm bin)/cypress open `.